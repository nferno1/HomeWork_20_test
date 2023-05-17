package nferno1.homework_20.view


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import nferno1.homework_20.R
import nferno1.homework_20.data.Photo
import nferno1.homework_20.databinding.RecyclerViewItemBinding
import com.bumptech.glide.Glide

class CustomAdapter(
    private val photos: List<Photo>,
    private val onClick: (Photo) -> Unit
) : Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = photos[position]
        holder.binding.apply {

            Glide.with(ivPhoto.context)
                .load(item.uri)
                .placeholder(R.drawable.photo)
                .into(ivPhoto)
            tvDate.text = item.date.take(10)
            root.setOnClickListener {
                onClick(item)
            }

        }
    }

    override fun getItemCount(): Int = photos.size
}