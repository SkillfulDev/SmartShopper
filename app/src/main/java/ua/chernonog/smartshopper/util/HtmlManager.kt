package ua.chernonog.smartshopper.util

import android.text.Html
import android.text.Spanned

object HtmlManager {
    fun convertHtmlStringToSpanned(text: String): Spanned {
        return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
    }

    fun convertSpannedToHtmlString(text: Spanned): String {
        return Html.toHtml(text, Html.FROM_HTML_MODE_COMPACT)
    }
}
