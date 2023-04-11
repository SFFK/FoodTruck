package com.cookandroid.foodtruck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.parsers.DocumentBuilderFactory

var text = ""

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.textView)

        // 키 값
        val key = "3ZHCQZynqk%2BkO9pdm0V0mZTYBzEpmGECP%2BSIb7MyiSKpr2SuSFqiS%2BskuQf8Vu7i9d%2FT5Py1ITuL6q8oYFSLfw%3D%3D"
        // 현재 페이지번호
        val pageNo = "&pageNo=0"
        // 한 페이지 결과 수
        val numOfRows = "&numOfRows=5"
        // 문서타입
        val type = "&type=xml"

        val url = "http://api.data.go.kr/openapi/tn_pubr_public_food_truck_permit_area_api?serviceKey=" + key + pageNo + numOfRows + type


        val thread = Thread(NetworkThread(url))

        // 스레드 시작
        thread.start()
        // 멀티 작업  안되게 하려면 start후 join 입력
        thread.join()

        textView.text = text
    }
}

class NetworkThread(val url : String) : Runnable {
    override fun run() {
        try {
            val xml : Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url)
            xml.documentElement.normalize()

            // 찾고자 하는 데이터 확인
            val list : NodeList = xml.getElementsByTagName("item")

            for(i in 0..list.length - 1) {
                val n : Node = list.item(i)

                if(n.getNodeType() == Node.ELEMENT_NODE) {
                    val elem = n as Element
                    val map = mutableMapOf<String, String>()

                    for(j in 0..elem.attributes.length - 1) {
                        map.putIfAbsent(elem.attributes.item(j).nodeName, elem.attributes.item(j).nodeValue)
                    }

                    println("1. 장소 : ${elem.getElementsByTagName("prmisnZoneNm").item(0).textContent}")
                    text += "1. 장소 : ${elem.getElementsByTagName("prmisnZoneNm").item(0).textContent} \n"

                    println("2. 위도 : ${elem.getElementsByTagName("latitude").item(0).textContent}")
                    text += "2. 위도 : ${elem.getElementsByTagName("latitude").item(0).textContent} \n"

                    println("3. 경도 : ${elem.getElementsByTagName("longitude").item(0).textContent}")
                    text += "3. 경도 : ${elem.getElementsByTagName("longitude").item(0).textContent} \n"

                }
            }
        } catch (e : Exception) {
            Log.d("TTT", "오픈API" + e.toString())
        }
    }
}
