package today.pathos.companya.codingtest.core.extension

import android.content.Context
import android.widget.Toast

fun Context.toastIt(msg : String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}