package eu.bakici.githubclient.repos

import android.graphics.Typeface
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.util.Log
import android.view.View

/**
 * Created on 15.01.18.
 */
class RepositoryViewModel(val repository: Repository) {


    var displayRepository: CharSequence = ""
        get() {
            val builder = SpannableStringBuilder()
            val name = repository.name
            builder.append(name)
            builder.setSpan(TypefaceSpan("sans-serif-medium"), 0, name.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.setSpan(StyleSpan(Typeface.BOLD), 0, name.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            builder.append("\n")
                    .append(repository.description)
            return builder
        }


    fun onRepositoryClick(view: View) {
        Log.d("RepoViewModel", repository.toString())
    }
}