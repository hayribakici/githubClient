package eu.bakici.githubclient.databinding

import android.databinding.BindingAdapter
import android.graphics.*
import android.text.TextUtils
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

/**
 * Created on 14.01.18.
 */
object ImageViewBindingAdapter {

    @BindingAdapter("url")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String) {
        if (TextUtils.isEmpty(url)) {
            return
        }
        val context = imageView.context
        Picasso.with(context)
                .load(url)
                .transform(CircleTransformation())
                .fit()
                .centerCrop()
                .into(imageView)
    }


    class CircleTransformation : Transformation {

        override fun transform(source: Bitmap): Bitmap {
            val size = Math.min(source.width, source.height)

            val x = (source.width - size) / 2
            val y = (source.width - size) / 2

            val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
            if (squaredBitmap != source) {
                source.recycle()
            }

            val bitmap = Bitmap.createBitmap(size, size, source.config)

            val canvas = Canvas(bitmap)
            val paint = Paint()
            val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            val matrix = Matrix()
            matrix.setTranslate(x * -1f, y * -1f)
            shader.setLocalMatrix(matrix)
            paint.shader = shader
            paint.isAntiAlias = true

            val r = size / 2f
            canvas.drawCircle(r, r, r, paint)

            squaredBitmap.recycle()
            return bitmap
        }

        override fun key(): String = "circle"
    }
}