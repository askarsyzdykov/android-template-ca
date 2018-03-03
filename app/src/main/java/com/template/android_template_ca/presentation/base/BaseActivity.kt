package com.template.android_template_ca.presentation.base

import android.os.Bundle
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity

abstract class BaseActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        /*val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val actionBar = supportActionBar
        if (actionBar == null) {
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(isDisplayHomeAsUpEnabled())
        }*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun isDisplayHomeAsUpEnabled(): Boolean

}