package fr.uha.gm.android.view
/*
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import fr.uha.hassenforder.teams.R

class FieldInputLayout : LinearLayout {
    private var line: View? = null
    private var hint: TextView? = null
    private var content: LinearLayout? = null
    private var error: TextView? = null
    private var errorIcon: ImageView? = null
    private var okColor : Int = Color.BLACK
    @ColorInt private var errorColor : Int = -0x4fffde
    @ColorInt private var wholeBackgroundColor : Int = -0x1f1f20
//    private val focusColor : Int = 0xff0001

    constructor(context: Context?) : super(context) {
        init(context, null, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs, defStyleAttr, defStyleRes)
    }

    private fun init(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val a = getContext().obtainStyledAttributes(
                attrs, R.styleable.FieldInputLayout, defStyleAttr, defStyleRes)
        val hintText = a.getString(R.styleable.FieldInputLayout_hint)
        val errorText = a.getString(R.styleable.FieldInputLayout_error)
        errorColor = a.getColor(R.styleable.FieldInputLayout_errorColor, errorColor)
        wholeBackgroundColor = a.getColor(R.styleable.FieldInputLayout_backgroundColor, wholeBackgroundColor)
        a.recycle()
        orientation = VERTICAL
        hint = TextView(context)
        hint!!.setBackgroundColor(wholeBackgroundColor)
        hint!!.setPadding(Companion.paddingLeft, Companion.paddingTop, Companion.paddingRight, 0)
        hint!!.setText(hintText)
        val textSize = 12
        hint!!.setTextSize(textSize.toFloat())
        addView(hint!!, -1, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        okColor = hint!!.getCurrentTextColor()
        content = LinearLayout(context)
        content!!.setOrientation(HORIZONTAL)
        content!!.setBackgroundColor(wholeBackgroundColor)
        content!!.setPadding(Companion.paddingLeft, 0, Companion.paddingRight, 0)
        addView(content!!, -1, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        errorIcon = ImageView(context)
        errorIcon!!.setImageResource(R.drawable.ic_baseline_error_24)
        errorIcon!!.setVisibility(INVISIBLE)
        errorIcon!!.setColorFilter(errorColor)
        errorIcon!!.setScaleType(ImageView.ScaleType.FIT_END)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 0.0F)
        params.gravity = Gravity.CENTER
        content!!.addView(errorIcon, -1, params)
        line = View(context)
        addView(line!!, -1, LayoutParams(LayoutParams.MATCH_PARENT, lineThickness))
        error = TextView(context)
        error!!.setText(errorText)
        error!!.setTextSize(textSize.toFloat())
        error!!.setTextColor(errorColor)
        error!!.setPadding(Companion.paddingLeft, 0, Companion.paddingRight, 0)
        addView(error!!, -1, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        if (child === line || child === hint || child === error || child === content) {
            super.addView(child, index, params)
        } else {
            content!!.addView(child, 0, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT))
        }
    }

    fun setHint(hintText: String) {
        hint!!.setText(hintText)
    }

    fun setHint(@StringRes hintId: Int) {
        hint!!.setText(hintId)
    }

    private fun applyError(hasError: Boolean) {
        if (hasError) {
            error!!.setVisibility(VISIBLE)
            errorIcon!!.setVisibility(VISIBLE)
            hint!!.setTextColor(errorColor)
            line!!.setBackgroundColor(errorColor)
        } else {
            error!!.setVisibility(GONE)
            errorIcon!!.setVisibility(INVISIBLE)
            hint!!.setTextColor(okColor)
            line!!.setBackgroundColor(okColor)
        }
    }

    fun setError(errorText: String?) {
        if (errorText == null) {
            error!!.setText("")
        } else {
            error!!.setText(errorText)
        }
        applyError(errorText != null)
    }

    fun setError(@StringRes errorId: Int) {
        if (errorId == 0) {
            error!!.setText("")
        } else {
            error!!.setText(errorId)
        }
        applyError(errorId != 0)
    }

    companion object {
        private const val paddingLeft = 44
        private const val paddingTop = 12
        private const val paddingRight = 32
        private const val lineThickness = 2
    }
}

 */