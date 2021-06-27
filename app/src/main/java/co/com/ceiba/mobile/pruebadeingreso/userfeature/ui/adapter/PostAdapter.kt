package co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.databinding.PostListItemBinding
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.Post

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {


    private var posts: MutableList<Post> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.post_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]

        holder.binder.title.text = post.title
        holder.binder.body.text = post.body
    }

    override fun getItemCount(): Int = posts.size

    fun updateItems(posts: List<Post>) {
        this.posts = posts.toMutableList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binder = PostListItemBinding.bind(view)

    }
}