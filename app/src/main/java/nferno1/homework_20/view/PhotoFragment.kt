package nferno1.homework_20.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import nferno1.homework_20.databinding.FragmentPhotoBinding


class PhotoFragment : Fragment() {
    private lateinit var binding: FragmentPhotoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoBinding.inflate(layoutInflater)
        arguments?.getString(MainFragment.PHOTO_KEY).let { uri ->
            Glide.with(binding.ivPhoto.context).load(uri).into(binding.ivPhoto)
        }
        return binding.root
    }


}