package com.cookandroid.foodtruck

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.cookandroid.foodtruck.databinding.ActivityRegionBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegionActivity : AppCompatActivity() {
    private val binding by lazy{ ActivityRegionBinding.inflate(layoutInflater)}
    private var regionDB : RegionDB? = null
    private var regionfirst : String =  "지역"
    private var regionList : MutableList<Region> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val items = mutableListOf<String>()
        val adapter = ArrayAdapter<String>(this, R.layout.simple_list_item_1, items)
        binding.regionView.adapter = adapter

        regionDB = RegionDB.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            regionList = regionDB!!.regionDao().getAll()
        }

        // 라디오그룹 체크변화 이벤트
        binding.radiogroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.seoul.id -> {
                    binding.seoul.setOnClickListener {
                        items.clear()
                        clearCheck(group)
                        regionfirst = "서울"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("서울") || regionList[i].ln.startsWith("서울")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.incheon.id -> {
                    binding.incheon.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "인천"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("인천") || regionList[i].ln.startsWith("인천")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.gyeonggi.id -> {
                    binding.gyeonggi.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "경기"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("경기") || regionList[i].ln.startsWith("경기")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.gangwon.id -> {
                    binding.gangwon.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "강원"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("강원") || regionList[i].ln.startsWith("강원")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }

        binding.radiogroup2.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.daejeon.id -> {
                    binding.daejeon.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "대전"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("대전") || regionList[i].ln.startsWith("대전")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.daegu.id -> {
                    binding.daegu.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "대구"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("대구") || regionList[i].ln.startsWith("대구")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.chungcheonNorth.id -> {
                    binding.chungcheonNorth.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "충청북도"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("충청북도") || regionList[i].ln.startsWith("충청북도")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.chungcheonSouth.id -> {
                    binding.chungcheonSouth.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "충청남도"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("충청남도") || regionList[i].ln.startsWith("충청남도")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }

        binding.radiogroup3.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.gwangju.id -> {
                    binding.gwangju.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "광주"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("광주") || regionList[i].ln.startsWith("광주")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.ulsan.id -> {
                    binding.ulsan.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "울산"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("울산") || regionList[i].ln.startsWith("울산")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.jellaNorth.id -> {
                    binding.jellaNorth.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "전라북도"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("전라북도") || regionList[i].ln.startsWith("전라북도")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.jellaSouth.id -> {
                    binding.jellaSouth.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "전라남도"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("전라남도") || regionList[i].ln.startsWith("전라남도")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }

        binding.radiogroup4.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.busan.id -> {
                    binding.busan.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "부산"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("부산") || regionList[i].ln.startsWith("부산")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.jeju.id -> {
                    binding.jeju.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "제주"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("제주") || regionList[i].ln.startsWith("제주")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.gyeongsangNorth.id -> {
                    binding.gyeongsangNorth.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "경상북도"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("경상북도") || regionList[i].ln.startsWith("경상북도")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }

                binding.gyeongsangSouth.id -> {
                    binding.gyeongsangSouth.setOnClickListener {
                        adapter.clear()
                        items.clear()
                        clearCheck(group)
                        regionfirst = "경상남도"

                        // DB ROOM update 코루틴 스코프
                        CoroutineScope(Dispatchers.IO).launch {
                            regionDB!!.regionDao().updateInv()                               // visible 속성을 false로

                            withContext(Dispatchers.Main) {
                                for(i in 0 until regionList.size) {
                                    if(regionList[i].rdn.startsWith("경상남도") || regionList[i].ln.startsWith("경상남도")) {
                                        // visible 속성 true 로 .. update
                                        regionDB!!.regionDao().update(Region(regionList[i].id, regionList[i].lat, regionList[i].lng, regionList[i].addr, regionList[i].rdn,
                                            regionList[i].ln, regionList[i].num, regionList[i].rnt, regionList[i].bgn, regionList[i].end, regionList[i].rst, regionList[i].ben, regionList[i].ins, regionList[i].chk, vis = true))
                                        // 리스트 아이템 추가
                                        items.add(regionList[i].addr)
                                    }
                                }
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            }
        }

        // 리셋버튼 클릭 이벤트
        binding.reset.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                regionDB!!.regionDao().updateVis()                       // visible 속성 초기화
                withContext(Dispatchers.Main) {
                    MainActivity().onMarker()
                }
            }
            allClearCheck()
            items.clear()
            adapter.notifyDataSetChanged()
        }

        // 마커표시 클릭 이벤트
        binding.showmarker.setOnClickListener {
            MainActivity().onMarker()
        }

        // 종료버튼 클릭 이벤트
        binding.close.setOnClickListener {
            finish()
        }
    }

    // 라디오버튼 체크해제 함수
    private fun clearCheck(group : RadioGroup) {
        when(group) {
            binding.radiogroup -> {
                binding.radiogroup2.clearCheck()
                binding.radiogroup3.clearCheck()
                binding.radiogroup4.clearCheck()
            }

            binding.radiogroup2 -> {
                binding.radiogroup.clearCheck()
                binding.radiogroup3.clearCheck()
                binding.radiogroup4.clearCheck()
            }

            binding.radiogroup3-> {
                binding.radiogroup.clearCheck()
                binding.radiogroup2.clearCheck()
                binding.radiogroup4.clearCheck()
            }

            binding.radiogroup4 -> {
                binding.radiogroup.clearCheck()
                binding.radiogroup2.clearCheck()
                binding.radiogroup3.clearCheck()
            }
        }
    }

    // 라디오버튼 체크초기화 함수
    private fun allClearCheck() {
        binding.radiogroup.clearCheck()
        binding.radiogroup2.clearCheck()
        binding.radiogroup3.clearCheck()
        binding.radiogroup4.clearCheck()
    }
}
