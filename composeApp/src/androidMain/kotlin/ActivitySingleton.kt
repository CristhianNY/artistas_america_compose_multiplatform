import androidx.activity.ComponentActivity

object ActivitySingleton {
    var activity: ComponentActivity? = null
}

fun ComponentActivity.setAsSingleton() {
    ActivitySingleton.activity = this
}