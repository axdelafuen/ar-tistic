package com.example.myapplication

import User
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

interface IPersistancemanager {
    public fun saveData(users:List<User>);
    public fun loadData():(List<User>);
}