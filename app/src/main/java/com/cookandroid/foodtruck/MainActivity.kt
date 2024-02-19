package com.cookandroid.foodtruck



import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.cookandroid.foodtruck.databinding.ActivityMainBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.w3c.dom.Text
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.math.ceil


var poistion : Int = 0
private lateinit var gMap : GoogleMap


class MainActivity : AppCompatActivity(), OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {
    private val binding by lazy {ActivityMainBinding.inflate(layoutInflater)}    // 뷰 바인딩
    private var regionDB : RegionDB? = null                                      // 데이터베이스
    var regionList : MutableList<Region> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 툴바를 액티비티의 앱바로 지정
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)            // 홈 버튼 활성화
        supportActionBar?.setDisplayShowTitleEnabled(false)          // 툴바 타이틀 안보이게
        binding.mainnavigationView.setNavigationItemSelectedListener(this)

        // DB 데이터를 읽어오기
        regionDB = RegionDB.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            regionList = regionDB!!.regionDao().getAll()
        }

        val mapFrag = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFrag.getMapAsync(this)

        // 키 값
        val key = "3ZHCQZynqk%2BkO9pdm0V0mZTYBzEpmGECP%2BSIb7MyiSKpr2SuSFqiS%2BskuQf8Vu7i9d%2FT5Py1ITuL6q8oYFSLfw%3D%3D"
        // 페이지
        val pageNo = "&pageNo=0"
        // 한 페이지 결과 수
        val numOfRows = "&numOfRows=350"
        // 문서타입
        val type = "&type=xml"
        // url
        val url = "http://api.data.go.kr/openapi/tn_pubr_public_food_truck_permit_area_api?serviceKey=" + key + pageNo + numOfRows + type

        // 스레드 객체 생성
        val thread = NetworkThread(url)
        // 스레드 시작
        thread.start()
        // 멀티 작업 안되게 하려면 start후 join 입력
        thread.join()


    }

    // 툴바 메뉴 버튼이 클릭 됐을떄 실행 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 클릭한 툴바 메뉴 아이템 id 마다 다르게 실행하도록 설정
        when (item!!.itemId) {
            android.R.id.home -> {
                // 왼쪽상단버튼 클릭시 네비게이션 드로어 열기
                findViewById<DrawerLayout>(R.id.drawer_layout).openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // 아이템 클릭 이벤트 처리함수
    override fun onNavigationItemSelected(item : MenuItem) : Boolean {
        when(item.itemId) {
            R.id.item_list -> {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent)
            }

            R.id.item_marker -> {
                val intent = Intent(this, RegionActivity::class.java)
                startActivity(intent)
            }

            R.id.item_notice -> {
                val intent = Intent(this, PostActivity::class.java)
                startActivity(intent)
            }
        }
        return false
    }

    // 위도와 경도 구하는 함수
    @Suppress("DEPRECATION")
    fun geoCoding(address: String): Location {
       return try {
            Geocoder(applicationContext, Locale.KOREA).getFromLocationName(address, 1)?.let {
                Location("").apply {
                    latitude = it[0].latitude
                    longitude = it[0].longitude
                }
            } ?: Location("").apply {
                latitude = 0.0
                longitude = 0.0
            }
        } catch (e: Exception) {
            // 일부 주소는 오류 발생
            // address를 substring으로 재 호출하면 이상없이 실행
            geoCoding(address.substring(1))
        }
    }

    override fun onMapReady(map : GoogleMap) {
        gMap = map
        // LatLng 객체를 생성해 위도, 경도를 지정
        val latLng = LatLng(35.866168, 127.801609)
        // 지정한 위도와 경도로 이동
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7.2f))

        onMarker()

        // 마커 클릭이벤트
        gMap.setOnMarkerClickListener {
            val dialog = MyDialog(this)

            // 마커에 타이틀과 허가구역명이 같으면 ...
            for(i in 0 until regionList.size) {
                if(it.title == regionList[i].addr) {
                    poistion = i
                    dialog.showDialog()
                }
            }
            true
        }

        // 줌인 이벤트
        binding.btnPlus.setOnClickListener {
            gMap.animateCamera(CameraUpdateFactory.zoomIn())
        }

        // 줌아웃 이벤트
        binding.btnMinus.setOnClickListener {
            gMap.animateCamera(CameraUpdateFactory.zoomOut())
        }
    }

    // 마커 초기화 함수
    fun deleteAllMarker() {
        gMap.clear()
    }

    // 마커 생성 함수
    fun onMarker() {
        deleteAllMarker()
        CoroutineScope(Dispatchers.IO).launch {
            regionDB = RegionDB.getInstance(this@MainActivity)
            regionList = regionDB!!.regionDao().getAll()

            withContext(Dispatchers.Main) {
                for(i in 0 until regionList.size) {
                    // chk가 true 면서 vis도 true 인것 ..
                     if(regionList[i].chk && regionList[i].vis) {
                         gMap.addMarker(MarkerOptions().position(LatLng(regionList[i].lat, regionList[i].lng)).title(regionList[i].addr)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                     }
                     // chk가 false 면서 vis도 true 인것 ..
                     else if (!regionList[i].chk && regionList[i].vis) {
                         gMap.addMarker(MarkerOptions().position(LatLng(regionList[i].lat, regionList[i].lng)).title(regionList[i].addr))
                    }
                }
            }
        }
    }

    // 스레드 클래스
    inner class NetworkThread(var url : String) : Thread() {
        override fun run() {
            try {
                val xml : Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)
                xml.documentElement.normalize()

                //찾고자 하는 데이터가 어느 노드 아래에 있는지 확인
                val list:NodeList = xml.getElementsByTagName("item")

                // 새로 추가된 데이터가 없으면 기존 데이터베이스에 저장된 내용 사용
                // gecoding의 작동시간이 너무 오래 걸리기 떄문에 ...
                if(list.length != regionList.size) {

                    // DB 초기화
                    CoroutineScope(Dispatchers.IO).launch {
                        regionDB!!.regionDao().deleteAll()
                    }

                    //list.length-1 만큼 얻고자 하는 태그의 정보를 가져온다
                    for(i in 0..list.length-1) {
                        val n:Node = list.item(i)

                        if(n.getNodeType() == Node.ELEMENT_NODE) {
                            val elem = n as Element
                            val map = mutableMapOf<String,String>()

                            for(j in 0 until elem.attributes.length - 1) {
                                map.putIfAbsent(elem.attributes.item(j).nodeName, elem.attributes.item(j).nodeValue)
                            }

                            var addrs = elem.getElementsByTagName("prmisnZoneNm").item(0).textContent
                            var rdns = elem.getElementsByTagName("rdnmadr").item(0).textContent
                            var lns = elem.getElementsByTagName("lnmadr").item(0).textContent
                            var nums = elem.getElementsByTagName("vhcleCo").item(0).textContent
                            var rnts = elem.getElementsByTagName("prmisnZoneRntfee").item(0).textContent
                            var bgns = elem.getElementsByTagName("beginDate").item(0).textContent
                            var ends = elem.getElementsByTagName("endDate").item(0).textContent
                            var rsts = elem.getElementsByTagName("rstde").item(0).textContent
                            var bens = elem.getElementsByTagName("lmttPrdlst").item(0).textContent
                            var inss = elem.getElementsByTagName("institutionNm").item(0).textContent

                            println("위도와 경도 생성중 ... $i")


                            runBlocking {
                                // 위도와 경도 데이터가 비어있으면 ..
                                if(elem.getElementsByTagName("latitude").item(0).textContent == "" || elem.getElementsByTagName("longitude").item(0).textContent == "") {

                                    // 도로명 주소가 비어있으면
                                    if(elem.getElementsByTagName("rdnmadr").item(0).textContent == "") {
                                        var lat1s = geoCoding(elem.getElementsByTagName("lnmadr").item(0).textContent).latitude
                                        var lng1s = geoCoding(elem.getElementsByTagName("lnmadr").item(0).textContent).longitude

                                        regionDB!!.regionDao().insert(Region(null, lat1s, lng1s, addrs, rdns, lns, nums, rnts, bgns, ends, rsts, bens, inss, chk = false, vis = true))
                                    }

                                    // 지번 주소가 비어있으면
                                    else if(elem.getElementsByTagName("lnmadr").item(0).textContent == "") {
                                        var lat1s = geoCoding(elem.getElementsByTagName("rdnmadr").item(0).textContent).latitude
                                        var lng1s = geoCoding(elem.getElementsByTagName("rdnmadr").item(0).textContent).longitude

                                        regionDB!!.regionDao().insert(Region(null, lat1s, lng1s, addrs, rdns, lns, nums, rnts, bgns, ends, rsts, bens, inss, chk = false, vis = true))
                                    }

                                    // 도로명 주소, 지번 둘다 있을 경우
                                    else {
                                        var lat1s = geoCoding(elem.getElementsByTagName("rdnmadr").item(0).textContent).latitude
                                        var lng1s = geoCoding(elem.getElementsByTagName("rdnmadr").item(0).textContent).longitude

                                        regionDB!!.regionDao().insert(Region(null, lat1s, lng1s, addrs, rdns, lns, nums, rnts, bgns, ends, rsts, bens, inss, chk = false, vis = true))
                                    }
                                }

                                // 위도와 경도 데이터가 있으면 ..
                                else {
                                    var lat1s = elem.getElementsByTagName("latitude").item(0).textContent.toDouble()
                                    var lng1s = elem.getElementsByTagName("longitude").item(0).textContent.toDouble()

                                    regionDB!!.regionDao().insert(Region(null, lat1s, lng1s, addrs, rdns, lns, nums, rnts, bgns, ends, rsts, bens, inss, chk = false, vis = true))
                                }
                            }
                        }
                    }
                }

            } catch (e: Exception) {
                Log.d("TTT", "오픈API"+e.toString())
            }
        }
    }
}


