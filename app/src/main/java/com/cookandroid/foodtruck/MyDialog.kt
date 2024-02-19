package com.cookandroid.foodtruck

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.cookandroid.foodtruck.databinding.ActivityMydialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyDialog(context: Context) : AppCompatActivity()
{
    private val dialog = Dialog(context)
    private lateinit var binding : ActivityMydialogBinding                       // 뷰바인딩
    private var regionDB : RegionDB? = null                                      // 데이터베이스
    var regionList : MutableList<Region> = mutableListOf()

    fun showDialog() {
        binding = ActivityMydialogBinding.inflate(dialog.layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        val button = binding.findButton

        regionDB = RegionDB.getInstance(this)
        CoroutineScope(Dispatchers.IO).launch {
            regionList = regionDB!!.regionDao().getAll()

            withContext(Dispatchers.Main) {
                binding.zone.text = regionList[poistion].addr
                binding.roadAddr.text = regionList[poistion].rdn
                binding.streetNumAddr.text = regionList[poistion].ln
                binding.rntFree.text = regionList[poistion].rnt
                binding.vhcleCo.text = regionList[poistion].num
                binding.beginDate.text = regionList[poistion].bgn
                binding.endDate.text = regionList[poistion].end
                binding.rstde.text = regionList[poistion].rst
                binding.lmttPrdlst.text = regionList[poistion].ben
                binding.institutionNm.text = regionList[poistion].ins

                if (!regionList[poistion].chk) {
                    button.text = "즐겨찾기"
                }
                else {
                    button.text = "즐겨찾기 해제"
                }
            }
        }

        button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                regionList = regionDB!!.regionDao().getAll()
                val clat = regionList[poistion].lat
                val clng = regionList[poistion].lng
                val caddr = regionList[poistion].addr
                val crdn = regionList[poistion].rdn
                val cln = regionList[poistion].ln
                val cnum = regionList[poistion].num
                val crnt = regionList[poistion].rnt
                val cbgn = regionList[poistion].bgn
                val cend = regionList[poistion].end
                val crst = regionList[poistion].rst
                val cben = regionList[poistion].ben
                val clns = regionList[poistion].ins
                val cvis = regionList[poistion].vis

                withContext(Dispatchers.Main) {
                    if(button.text == "즐겨찾기") {
                        button.text = "즐겨찾기 해제"
                        regionDB!!.regionDao().update(Region(poistion + 1, clat, clng, caddr, crdn, cln, cnum, crnt, cbgn, cend, crst, cben, clns, chk = true, cvis))
                        MainActivity().onMarker()
                    } else {
                        button.text = "즐겨찾기"
                        regionDB!!.regionDao().update(Region(poistion + 1, clat, clng, caddr, crdn, cln, cnum, crnt, cbgn, cend, crst, cben, clns, chk = false, cvis))
                        MainActivity().onMarker()
                    }
                }
            }
        }


        binding.cancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }
}