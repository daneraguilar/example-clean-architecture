package co.com.ceiba.mobile.pruebadeingreso.userfeature.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.databinding.UserListItemBinding
import co.com.ceiba.mobile.pruebadeingreso.userfeature.domain.entity.User

class UserAdapter(
    private val isEmptyToFilter: (isEmpty: Boolean) -> Unit,
    private val showPosts: (user: User) -> Unit
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    private var filter: String = ""
    private var users: MutableList<User> = mutableListOf()


    private fun getFilteredList(counters: List<User>, filter: String): List<User> =
        counters.filter { it.name.contains(filter, true) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.user_list_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getFilteredList(users, filter)[position]

        holder.binder.name.text = user.name
        holder.binder.email.text = user.email
        holder.binder.phone.text = user.phone
        holder.binder.btnViewPost.setOnClickListener {
            it.isEnabled = false
            showPosts(user)
        }
    }

    override fun getItemCount(): Int = getFilteredList(users, filter).size

    fun updateItems(users: List<User>) {
        this.users = users.toMutableList()
        notifyDataSetChanged()
    }

    fun filterUsers(filterText: String) {
        filter = filterText
        isEmptyToFilter(getFilteredList(users, filter).isEmpty())
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binder = UserListItemBinding.bind(view)

    }
}