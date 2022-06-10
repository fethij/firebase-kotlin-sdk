@file:JsModule("firebase/app")
@file:JsNonModule

package dev.gitlive.firebase.externals

external fun initializeApp(options: Any, name: String = definedExternally): FirebaseApp

external fun getApp(name: String = definedExternally): FirebaseApp

external fun getApps(): List<FirebaseApp>

external interface FirebaseApp {
    val automaticDataCollectionEnabled: Boolean
    val name: String
    val options: FirebaseOptions
}

external interface FirebaseOptions {
    val apiKey: String
    val appId : String
    val authDomain: String?
    val databaseURL: String?
    val measurementId: String?
    val messagingSenderId: String?
    val gaTrackingId: String?
    val projectId: String?
    val storageBucket: String?
}
