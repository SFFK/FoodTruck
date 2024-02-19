package com.cookandroid.foodtruck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.foodtruck.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityPostBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val list = ArrayList<PostData>()
        list.add(PostData("Food Truck v 1.0", "메인화면 구성, 공공데이터 불러오기"))
        list.add(PostData("Food Truck v 1.1", "DB ROOM 데이터 저장, GoogleMap 이용"))
        list.add(PostData("Food Truck v 1.2", "마커 클릭 이벤트 구현, 즐겨찾기 구현"))
        list.add(PostData("Food Truck v 1.3", "Dialog 구현"))
        list.add(PostData("Food Truck v 1.4", "지역별 마커 표시 구현"))
        list.add(PostData("Food Truck v 1.5", "네비게이션바 생성, 메뉴 아이템 추가"))
        list.add(PostData("Food Truck v 1.6", "gecoding()으로 인한 앱 실행 딜레이 해결"))

        val adapter = RecyclerItemAdapter(list)
        binding.postList.adapter = adapter
    }
}