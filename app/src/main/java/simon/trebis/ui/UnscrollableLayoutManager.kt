package simon.trebis.ui

import android.content.Context
import android.support.v7.widget.LinearLayoutManager


class UnscrollableLayoutManager(context: Context, orientation: Int)
    : LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) {

    private var isScrollEnabled = true

    fun setScrollEnabled(flag: Boolean) {
        this.isScrollEnabled = flag
    }

    override fun canScrollVertically(): Boolean {
        return isScrollEnabled && super.canScrollVertically()
    }

}
