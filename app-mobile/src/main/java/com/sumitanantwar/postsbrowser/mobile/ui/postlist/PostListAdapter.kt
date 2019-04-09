package com.sumitanantwar.postsbrowser.mobile.ui.postlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sumitanantwar.postsbrowser.mobile.R
import com.sumitanantwar.presentation.model.Post
import com.sumitanantwar.repository.model.User
import javax.inject.Inject

interface PostListAdapterCallBackListener {
    fun onClickPost(post: Post, user: User)
}

class PostListAdapter @Inject constructor() : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    private var callBackListener: PostListAdapterCallBackListener? = null
    private var posts: List<Post> = emptyList()
    private var users: List<User> = emptyList()

    //======= Adapter Lifecycle =======
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cellView = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_list_item, parent, false)
        return ViewHolder(cellView)
    }

    override fun getItemCount(): Int {
        return posts.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = posts[position]
        val user = users.find { it.id == post.userId }
        holder.set(post, user, callBackListener)
    }


    //======= Public =======
    fun updatePosts(posts: List<Post>, users: List<User>) {
        this.posts = posts
        this.users = users
        notifyDataSetChanged()
    }

    fun setCallBackListener(callBackListener: PostListAdapterCallBackListener) {
        this.callBackListener = callBackListener
    }


    //======= ViewHolder =======
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var postId:         TextView
        var title:          TextView
        var userId:         TextView
        var profileImage:   ImageView

        init {
            postId          = view.findViewById(R.id.text_post_id)
            title           = view.findViewById(R.id.text_post_title)
            userId          = view.findViewById(R.id.text_userid)
            profileImage    = view.findViewById(R.id.image_profile)
        }

        fun set(post: Post?, user: User?, callBackListener: PostListAdapterCallBackListener?) {

            if (post == null) return

            this.postId.text    = post.id.toString()
            this.title.text     = post.title
            this.userId.text    = post.userId.toString()

            if (user != null) {
                Glide.with(itemView)
                    .load(user.profileImageUrl)
                    .into(profileImage)
            }

            var color = this.itemView.resources.getColor(R.color.cell_odd)
            if (this.adapterPosition % 2 == 0) {
                color = this.itemView.resources.getColor(R.color.cell_even)
            }

            this.itemView.setBackgroundColor(color)

            // Don't implement the Click Listener
            // if the user details haven't been fetched yet
            if (user == null) return

            this.itemView.setOnClickListener {
                callBackListener?.onClickPost(post, user)
            }
        }
    }
}