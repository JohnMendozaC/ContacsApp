package com.example.contactsapp.infrastructure.dblocal

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao {

    @Query("SELECT * FROM Contact WHERE gender = :gender")
    fun getContactFilterByGender(gender: String): List<ContactEntity>

    @Query("SELECT * FROM Contact ORDER BY age ASC")
    fun getContactSortedByAge(): List<ContactEntity>

    @Query("SELECT * FROM Contact ORDER BY lastName ASC")
    fun getContactSortedByLastName(): List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<ContactEntity>)
}