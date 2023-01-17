package fr.uha.gm.android.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import fr.uha.gm.projet.R

class ItemSwipeCallback(context: Context, directions: Int, private val listener: SwipeListener) : ItemTouchHelper.SimpleCallback(0, directions) {
    interface SwipeListener {
        fun onSwiped(direction: Int, position: Int)
    }

    private val iconDelete: Drawable =
        context.getResources().getDrawable(android.R.drawable.ic_menu_delete, null)
    private val iconEdit: Drawable =
        context.getResources().getDrawable(android.R.drawable.ic_menu_edit, null)
    private val backgroundDelete: ColorDrawable by lazy {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ColorDrawable(context.getResources().getColor(R.color.color_delete, null))
/*        } else {
            ColorDrawable(context.getResources().getColor(R.color.color_delete))
        }
*/    }
    private val backgroundEdit: ColorDrawable by lazy {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ColorDrawable(context.getResources().getColor(R.color.color_edit, null))
/*        } else {
            ColorDrawable(context.getResources().getColor(R.color.color_edit))
        }
*/    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.layoutPosition
        listener.onSwiped(direction, position)
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val item = viewHolder.itemView
        val backgroundCornerOffset = 20
        if (dX > 0) {
            backgroundEdit.setBounds(
                item.left, item.top,
                (item.left + dX + backgroundCornerOffset).toInt(), item.bottom
            )
            backgroundEdit.draw(c)
            val iconMargin = (item.height - iconEdit.getIntrinsicHeight()) / 2
            val iconTop = item.top + iconMargin
            val iconBottom = iconTop + iconEdit.getIntrinsicHeight()
            val iconLeft = item.left + iconMargin
            val iconRight = item.left + iconMargin + iconEdit.getIntrinsicWidth()
            iconEdit.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            iconEdit.draw(c)
        } else if (dX < 0) {
            backgroundDelete.setBounds(
                (item.right + dX - backgroundCornerOffset).toInt(), item.top,
                item.right, item.bottom
            )
            backgroundDelete.draw(c)
            val iconMargin = (item.height - iconDelete.getIntrinsicHeight()) / 2
            val iconTop = item.top + iconMargin
            val iconBottom = iconTop + iconDelete.getIntrinsicHeight()
            val iconLeft = item.right - iconMargin - iconDelete.getIntrinsicWidth()
            val iconRight = item.right - iconMargin
            iconDelete.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            iconDelete.draw(c)
        }
    }

}
