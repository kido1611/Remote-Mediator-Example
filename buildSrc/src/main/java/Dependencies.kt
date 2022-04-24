object Versions {
    const val compose = "1.1.1"
    const val lifecycleRuntime = "2.4.1"
    const val activityCompose = "1.4.0"
    const val hilt = "2.41"
    const val paging = "3.1.1"
    const val pagingCompose = "1.0.0-alpha14"
    const val okhttp = "4.9.1"
    const val retrofit = "2.9.0"
    const val room = "2.4.1"
    const val navigationCompose = "2.4.2"
}

object Dependencies {
    object Paging {
        const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.paging}"
        const val pagingCompose = "androidx.paging:paging-compose:${Versions.pagingCompose}"
    }

    object Room {
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        const val roomPaging = "androidx.room:room-paging:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    }

    object Hilt {
        const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
    }

    object Okhttp {
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
        const val converterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
    }
}