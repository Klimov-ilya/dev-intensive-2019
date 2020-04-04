package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_chat_group.view.*
import kotlinx.android.synthetic.main.item_chat_single.view.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType

class ChatAdapter(
    private val listener: (ChatItem) -> Unit
) : RecyclerView.Adapter<ChatAdapter.ChatItemViewHolder>() {
    var items: List<ChatItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            SINGLE_TYPE -> SingleViewHolder(inflater.inflate(R.layout.item_chat_single, parent, false))
            GROUP_TYPE -> GroupViewHolder(inflater.inflate(R.layout.item_chat_group, parent, false))
            else -> ArchiveViewHolder(inflater.inflate(R.layout.item_chat_single, parent, false))
        }
    }


    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = when(items[position].chatType) {
        ChatType.SINGLE -> SINGLE_TYPE
        ChatType.GROUP -> GROUP_TYPE
        ChatType.ARCHIVE -> ARCHIVE_TYPE
    }

    override fun onBindViewHolder(holder: ChatItemViewHolder, position: Int) =
        holder.bind(items[position], listener)

    fun updateData(data: List<ChatItem>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldPos: Int, newPos: Int): Boolean =
                items[oldPos].id == data[newPos].id

            override fun areContentsTheSame(oldPos: Int, newPos: Int): Boolean =
                items[oldPos].hashCode() == data[newPos].hashCode()

            override fun getOldListSize() = items.size

            override fun getNewListSize() = data.size
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = data
        diffResult.dispatchUpdatesTo(this)
    }

    abstract inner class ChatItemViewHolder(convertView: View) : RecyclerView.ViewHolder(convertView), LayoutContainer {
        override val containerView: View get() = itemView

        abstract fun bind(item: ChatItem, listener: (ChatItem) -> Unit)
    }

    inner class SingleViewHolder(view: View) : ChatItemViewHolder(view), ItemTouchViewHolder {

        override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
            with(containerView) {
                if (item.avatar == null) {
                    Glide.with(itemView).clear(iv_avatar_single)
                } else {
                    Glide
                        .with(itemView)
                        .load(item.avatar)
                        .into(iv_avatar_single)
                }
                tv_title_single.text = item.title
                tv_message_single.text = item.shortDescription
                sv_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE
                tv_date_single.text = item.lastMessageDate
                tv_date_single.visibility = if (item.lastMessageDate != null) View.VISIBLE else View.GONE
                tv_counter_single.visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
                tv_counter_single.text = item.messageCount.toString()
                setOnClickListener {
                    listener(item)
                }
            }
        }

        override fun onItemSelected() {
             itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    inner class GroupViewHolder(view: View) : ChatItemViewHolder(view), ItemTouchViewHolder {

        override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
            with(itemView) {
                tv_title_group.text = item.title

                tv_message_group.text = item.shortDescription

                tv_date_group.text = item.lastMessageDate
                tv_date_group.visibility = if (item.lastMessageDate != null) View.VISIBLE else View.GONE

                tv_counter_group.visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
                tv_counter_group.text = item.messageCount.toString()

                tv_message_author.visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
                tv_message_author.text = item.author

                setOnClickListener { listener(item) }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    inner class ArchiveViewHolder(view: View) : ChatItemViewHolder(view), ItemTouchViewHolder {

        override fun bind(item: ChatItem, listener: (ChatItem) -> Unit) {
            with(itemView) {
                tv_message_group.text = item.shortDescription

                tv_date_group.text = item.lastMessageDate
                tv_date_group.visibility = if (item.lastMessageDate != null) View.VISIBLE else View.GONE

                tv_counter_group.visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
                tv_counter_group.text = item.messageCount.toString()

                tv_message_author.visibility = if (item.messageCount > 0) View.VISIBLE else View.GONE
                tv_message_author.text = item.author

                setOnClickListener { listener(item) }
            }
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemCleared() {
            itemView.setBackgroundColor(Color.WHITE)
        }
    }

    companion object {
        private const val ARCHIVE_TYPE = 0
        private const val SINGLE_TYPE = 1
        private const val GROUP_TYPE = 2
    }
}