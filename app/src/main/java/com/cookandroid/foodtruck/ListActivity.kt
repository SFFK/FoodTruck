package com.cookandroid.foodtruck

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import com.cookandroid.foodtruck.databinding.ActivityListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListActivity : AppCompatActivity() {
    private val binding by lazy{ActivityListBinding.inflate(layoutInflater)}     // 뷰 바인딩
    private var regionDB : RegionDB? = null                                      // 데이터베이스
    var regionList : MutableList<Region> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val items = mutableListOf<String>()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        binding.listview.adapter = adapter

        // DB ROOM 데이터 조회
        regionDB = RegionDB.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            regionList = regionDB!!.regionDao().getAll()

            // 리스트 아이템 추가
            withContext(Dispatchers.Main) {
                for(i in 0 until regionList.size) {
                    if(regionList[i].chk) {
                        items.add(regionList[i].addr)
                    }
                }

                adapter.notifyDataSetChanged()
            }
        }


        // 삭제버튼 클릭 이벤트
        binding.deleteAll.setOnClickListener {
            // DB ROOM update 코루틴 스코프
            CoroutineScope(Dispatchers.IO).launch {
                regionList = regionDB!!.regionDao().getAll()

                // chk value를 false로 update
                for(i in 0 until regionList.size) {
                    regionDB!!.regionDao().updateChk()
                }

                withContext(Dispatchers.Main) {
                    MainActivity().onMarker()
                }
            }
            items.clear()                           // 모든 아이템 초기화
            adapter.notifyDataSetChanged()          // 어댑터 변화업데이트
        }

        binding.close.setOnClickListener {
            finish()
        }
    }
}