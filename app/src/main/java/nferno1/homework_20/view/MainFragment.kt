package nferno1.homework_20.view

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import nferno1.homework_20.MainActivity
import nferno1.homework_20.MyApp
import nferno1.homework_20.R
import nferno1.homework_20.data.Photo
import nferno1.homework_20.databinding.FragmentMainBinding
import nferno1.homework_20.presentation.MainViewModel
import nferno1.homework_20.view.MainFragment.Companion.NOTIFICATION_ID as NOTIFICATION_ID1


@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                findNavController().navigate(R.id.action_mainFragment_to_cameraFragment)
            } else {
                Toast.makeText(context, "camera permissions isn't granted", Toast.LENGTH_LONG)
                    .show()
            }

        }

    private val launcherMaps =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                findNavController().navigate(R.id.action_mainFragment_to_mapsFragment)
            } else {
                Toast.makeText(context, "location permissions isn't granted",
                    Toast.LENGTH_LONG)
                    .show()
            }

        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        binding.bPhoto.setOnClickListener { checkCameraPermission() }
        binding.bMap.setOnClickListener {
            checkMapPermission()
            createNotificaion()
        }
        lifecycleScope.launchWhenCreated {
            viewModel.allPhotos.collect { photos ->
                val adapter = CustomAdapter(photos) { photo -> onItemClick(photo) }
                binding.rvMain.adapter = adapter

            }
        }
        return binding.root
    }

    private fun createNotificaion() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.getActivity(requireContext(), 0,
                intent, PendingIntent.FLAG_IMMUTABLE)
        else PendingIntent.getActivity(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notification = NotificationCompat.Builder(requireContext(), MyApp.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_active)
            .setContentText("Let's do sightseeing today")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID1, notification)
    }

    private fun onItemClick(item: Photo) {
        val bundle = bundleOf(
            PHOTO_KEY to item.uri
        )
        findNavController().navigate(R.id.action_mainFragment_to_photoFragment, bundle)
    }

    private fun checkMapPermission() {
        val isAllGranted = REQUEST_PERMISSIONS_MAP.all { permission ->
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    permission
                )
            } == PackageManager.PERMISSION_GRANTED
        }

        if (isAllGranted) {
            Toast.makeText(context, "location permission is Granted", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_mainFragment_to_mapsFragment)
        } else {
            launcherMaps.launch(REQUEST_PERMISSIONS_MAP)
        }
    }

    private fun checkCameraPermission() {
        val isAllGranted = REQUEST_PERMISSIONS.all { permission ->
            context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    permission
                )
            } == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGranted) {
            Toast.makeText(context, "camera permission is Granted", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_mainFragment_to_cameraFragment)
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }

    companion object {
        const val PHOTO_KEY: String = "key_photo"
        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

        private val REQUEST_PERMISSIONS_MAP: Array<String> = buildList {
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
            add(Manifest.permission.ACCESS_FINE_LOCATION)
        }.toTypedArray()
        private const val NOTIFICATION_ID = 1234
    }
}