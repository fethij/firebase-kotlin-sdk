package dev.gitlive.firebase.auth

import dev.gitlive.firebase.auth.externals.MultiFactorUser
import kotlinx.coroutines.await
import kotlin.js.Date
import dev.gitlive.firebase.auth.externals.MultiFactorAssertion as JsMultiFactorAssertion
import dev.gitlive.firebase.auth.externals.MultiFactorInfo as JsMultiFactorInfo
import dev.gitlive.firebase.auth.externals.MultiFactorResolver as JsMultiFactorResolver
import dev.gitlive.firebase.auth.externals.MultiFactorSession as JsMultiFactorSession

val MultiFactor.js get() = js

actual class MultiFactor(internal val js: MultiFactorUser) {
    actual val enrolledFactors: List<MultiFactorInfo>
        get() = rethrow { js.enrolledFactors.map { MultiFactorInfo(it) } }
    actual suspend fun enroll(multiFactorAssertion: MultiFactorAssertion, displayName: String?) =
        rethrow { js.enroll(multiFactorAssertion.js, displayName).await() }
    actual suspend fun getSession(): MultiFactorSession =
        rethrow { MultiFactorSession(js.getSession().await()) }
    actual suspend fun unenroll(multiFactorInfo: MultiFactorInfo) =
        rethrow { js.unenroll(multiFactorInfo.js).await() }
    actual suspend fun unenroll(factorUid: String) =
        rethrow { js.unenroll(factorUid).await() }
}

val MultiFactorInfo.js get() = js

actual class MultiFactorInfo(internal val js: JsMultiFactorInfo) {
    actual val displayName: String?
        get() = rethrow { js.displayName }
    actual val enrollmentTime: Double
        get() = rethrow { (Date(js.enrollmentTime).getTime() / 1000.0) }
    actual val factorId: String
        get() = rethrow { js.factorId }
    actual val uid: String
        get() = rethrow { js.uid }
}

val MultiFactorAssertion.js get() = js

actual class MultiFactorAssertion(internal val js: JsMultiFactorAssertion) {
    actual val factorId: String
        get() = rethrow { js.factorId }
}

val MultiFactorSession.js get() = js

actual class MultiFactorSession(internal val js: JsMultiFactorSession)

val MultiFactorResolver.js get() = js

actual class MultiFactorResolver(internal val js: JsMultiFactorResolver) {
    actual val auth: FirebaseAuth = rethrow { FirebaseAuth(js.auth) }
    actual val hints: List<MultiFactorInfo> = rethrow { js.hints.map { MultiFactorInfo(it) } }
    actual val session: MultiFactorSession = rethrow { MultiFactorSession(js.session) }

    actual suspend fun resolveSignIn(assertion: MultiFactorAssertion): AuthResult = rethrow { AuthResult(js.resolveSignIn(assertion.js).await()) }
}
