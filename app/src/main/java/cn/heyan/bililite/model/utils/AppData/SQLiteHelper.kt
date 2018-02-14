package cn.heyan.bililite.model.utils.AppData

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * SQLiteHelper
 * Created by HeYan on 2018/2/13 0013.
 */

object SQLiteHelper{

    var sQLite:SQLiteAppData ? = null

    fun init(context: Context){
        sQLite = SQLiteAppData(context)
    }

}

class SQLiteAppData(context: Context) :
        SQLiteOpenHelper(context,"AppData",null,1){

    companion object {
        val SHIELD_UP_TABLE = "create table if not exists shieldUP(" +
                "id integer primary key autoincrement," +
                "name text," +
                "mid integer)"

        val SHIELD_WORD_TABLE = "create table if not exists shieldWORD(" +
                "id integer primary key autoincrement," +
                "word text)"

        val SHIELD_TID_TABLE = "create table if not exists shieldTID(" +
                "id integer primary key autoincrement," +
                "tid integer)"
    }

    override fun onCreate(p0: SQLiteDatabase) {

        p0.execSQL(SHIELD_UP_TABLE)
        p0.execSQL(SHIELD_WORD_TABLE)
        p0.execSQL(SHIELD_TID_TABLE)

    }

    override fun onUpgrade(p0: SQLiteDatabase, p1: Int, p2: Int) {}

    fun getTidList():MutableList<Int>{
        val list:MutableList<Int> = mutableListOf()
        val db = readableDatabase
        val cursor = db.query("shieldTID", null, null, null, null, null, "id desc")
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            list.add(cursor.getInt(1))
            cursor.moveToNext()
        }
        cursor.close()
        db.close()
        return list
    }

    fun addTid(tid:Int){
        val write = writableDatabase
        val contentValue = ContentValues()
        contentValue.put("tid",tid)
        write.insert("shieldTID",null,contentValue)
        write.close()
    }

    fun deleteTid(tid:Int){
        val write = writableDatabase
        write.delete("shieldTID","tid=?", arrayOf(tid.toString()))
        write.close()
    }

    fun getWordList():MutableList<String>{
        val list:MutableList<String> = mutableListOf()
        val read = readableDatabase
        val cursor = read.query("shieldWORD", null, null, null, null, null, "id desc")
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            list.add(cursor.getString(1))
            cursor.moveToNext()
        }
        cursor.close()
        read.close()
        return list
    }

    fun addWord(word:String){
        val write = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("word",word)
        write.insert("shieldWORD",null,contentValues)
    }

    fun deleteWord(word: String){
        val write = writableDatabase
        write.delete("shieldWORD","word=?", arrayOf(word))
    }

    fun getUpList():MutableList<ShieldUp>{

        val list:MutableList<ShieldUp> = mutableListOf()

        val read = readableDatabase
        val cursor = read.query("shieldUP", null, null, null, null, null, "id desc")
        cursor.moveToFirst()
        while (!cursor.isAfterLast){
            val shieldUp = ShieldUp()
            shieldUp.name = cursor.getString(cursor.getColumnIndex("name"))
            shieldUp.mid = cursor.getInt(cursor.getColumnIndex("mid"))
            list.add(shieldUp)
            cursor.moveToNext()
        }
        cursor.close()
        read.close()

        return list

    }

    fun addUp(up:ShieldUp){
        val write = writableDatabase
        val contentValues = ContentValues()
        contentValues.put("name",up.name)
        contentValues.put("mid",up.mid)
        write.insert("shieldUP",null,contentValues)
        write.close()
    }

    fun deleteUp(name:String){
        val write = writableDatabase
        write.delete("shieldUP","name=?", arrayOf(name))
        write.close()
    }

    fun deleteUp(tid:Int){
        val write = writableDatabase
        write.delete("shieldUP","tid=?", arrayOf(tid.toString()))
        write.close()
    }

}

/**
 * ShieldUp
 * 屏蔽Up主实体类
 */
class ShieldUp{

    //屏蔽的Up主名字
    var name = ""

    //屏蔽的Up主id
    var mid = 0

}