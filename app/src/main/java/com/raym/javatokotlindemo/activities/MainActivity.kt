package com.raym.javatokotlindemo.activities

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import android.os.Bundle
import android.content.Intent
import android.view.View
import com.raym.javatokotlindemo.app.Constants

import kotlinx.android.synthetic.main.activity_main.*;
import com.raym.javatokotlindemo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

    }

    /** Save app username in SharedPreferences  */
    fun saveName(view: View?) {

    }

    /** Search repositories on github  */
    fun listRepositories(view: View?) {
        if (isNotEmpty(etRepoName, inputLayoutRepoName)){
            val queryRepo = etRepoName.text.toString()
            val repoLanguage = etLanguage.text.toString()

            val intent = Intent(this@MainActivity, DisplayActivity::class.java)
            intent.putExtra(Constants.KEY_QUERY_TYPE, Constants.SEARCH_BY_REPO)
            intent.putExtra(Constants.KEY_REPO_SEARCH, queryRepo)
            intent.putExtra(Constants.KEY_LANGUAGE, repoLanguage)
            startActivity(intent)
        }
    }

    /** Search repositories of a particular github user  */
    fun listUserRepositories(view: View?) {
        if (isNotEmpty(etGithubUser, inputLayoutGithubUser)){
            val githubUser = etGithubUser.text.toString()

            val intent = Intent(this@MainActivity, DisplayActivity::class.java)
            intent.putExtra(Constants.KEY_QUERY_TYPE, Constants.SEARCH_BY_USER)
            intent.putExtra(Constants.KEY_GITHUB_USER, githubUser)
            startActivity(intent)
        }
    }

    fun isNotEmpty(editText: EditText, textInputLayout: TextInputLayout) : Boolean{
        if (editText.text.toString().isEmpty()){
            textInputLayout.error = "Cannot be blank"
            return false
        }else{
            textInputLayout.isErrorEnabled = false
            return true
        }
    }

    companion object{
        private val TAG: String = MainActivity::class.java.simpleName
    }
}