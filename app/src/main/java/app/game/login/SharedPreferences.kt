package app.game.login

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SharedPref(private val context: Context) {
    companion object{
        private val Context.datastore: DataStore<Preferences> by preferencesDataStore("login")
        val LOGIN_USERNAME = stringPreferencesKey("userLogin")
        val LOGIN_PASSWORD = stringPreferencesKey("userPassword")
    }

    val getPassword: Flow<String> = context.datastore.data
        .map {
                preferences -> preferences[LOGIN_PASSWORD] ?: ""
        }

    val getUserName: Flow<String> = context.datastore.data
        .map {
                preferences -> preferences[LOGIN_USERNAME] ?: ""
        }

    suspend fun savePassword(layout:String) {
        context.datastore.edit{
                preferences -> preferences[LOGIN_PASSWORD] = layout
        }
    }

    suspend fun saveUsername(sortView:String) {
        context.datastore.edit{
                preferences -> preferences[LOGIN_USERNAME] = sortView
        }
    }
}