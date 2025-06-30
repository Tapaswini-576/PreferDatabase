package com.example.preferdatabase.data.remote



import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await

object AuthRepo {
    private val auth = FirebaseAuth.getInstance()

    suspend fun login(email: String, pass: String): Result<Unit> = runCatching {
        auth.signInWithEmailAndPassword(email, pass).await()
        // success → cache locally
        val token = auth.currentUser?.uid ?: ""   // simple token
        com.example.preferdatabase.data.LoginPrefs.save(email, token)
    }

    suspend fun register(email: String, pass: String): Result<Unit> = runCatching {
        auth.createUserWithEmailAndPassword(email, pass).await()
        val token = auth.currentUser?.uid ?: ""
        com.example.preferdatabas.data.LoginPrefs.save(email, token)
    }
}

object AuthState {
    private val auth = FirebaseAuth.getInstance()
    val loggedInFlow: StateFlow<Boolean> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { trySend(it.currentUser != null) }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }.stateIn(
        scope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob()),
        started = SharingStarted.Eagerly,
        initialValue = auth.currentUser != null
    )
}