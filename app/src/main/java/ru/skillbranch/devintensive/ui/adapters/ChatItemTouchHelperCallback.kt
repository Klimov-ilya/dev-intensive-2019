package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem

class ChatItemTouchHelperCallback(
    val adapter: ChatAdapter,
    val swipeListener: (ChatItem) -> Unit
) : ItemTouchHelper.Callback() {
    // Для отрисовки прямоугольника, когда происходит свайп
    private val bgRect = RectF()
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    // Для отрисовки иконки под свайпом
    private val iconBounds = Rect()

    override fun getMovementFlags(rv: RecyclerView, vh: ViewHolder): Int =
        if (vh is ItemTouchViewHolder) {
            makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.START)
        } else {
            makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.START)
        }

    override fun onMove(rv: RecyclerView, vh: ViewHolder, target: ViewHolder): Boolean = false

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        swipeListener(adapter.items[viewHolder.adapterPosition])
    }

    override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemTouchViewHolder) {
            viewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
        if (viewHolder is ItemTouchViewHolder) {
            viewHolder.onItemCleared()
        }
        super.clearView(recyclerView, viewHolder)
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            drawBackground(canvas, itemView, dX)
            drawIcon(canvas, itemView, dX)
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    // Отрисовка иконки под свайпом
    private fun drawIcon(canvas: Canvas, itemView: View, dX: Float) {
        val icon = itemView.resources.getDrawable(R.drawable.ic_archive_black_24dp, itemView.context.theme)
        val iconSize = itemView.resources.getDimensionPixelSize(R.dimen.icon_size)
        val space = itemView.resources.getDimensionPixelSize(R.dimen.spacing_normal_16)

        val margin = (itemView.bottom - itemView.top - iconSize) / 2
        with(iconBounds) {
            left = itemView.right + dX.toInt() + space
            top = itemView.top + margin
            right = itemView.right + dX.toInt() + space + iconSize
            bottom = itemView.bottom - margin
        }
        icon.bounds = iconBounds
        icon.draw(canvas)
    }

    // Отрисовка прямоугольника на заднем фоне
    private fun drawBackground(canvas: Canvas, itemView: View, dX: Float) {
        with(bgRect) {
            left = itemView.left.toFloat() + dX
            top = itemView.top.toFloat()
            right = itemView.right.toFloat()
            bottom = itemView.bottom.toFloat()
        }

        with(bgPaint) {
            color = itemView.resources.getColor(R.color.color_primary_dark, itemView.context.theme)
        }
        canvas.drawRect(bgRect, bgPaint)
    }
}

interface ItemTouchViewHolder {
    fun onItemSelected()
    fun onItemCleared()
}