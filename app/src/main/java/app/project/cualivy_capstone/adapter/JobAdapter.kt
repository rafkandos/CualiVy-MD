package app.project.cualivy_capstone.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.project.cualivy_capstone.R
import app.project.cualivy_capstone.databinding.ItemJobBinding
import app.project.cualivy_capstone.detail.DetailActivity

import app.project.cualivy_capstone.response.Detail


class JobAdapter(private val listReview: ArrayList<String>) : RecyclerView.Adapter<JobAdapter.ViewHolder>() {

//    private var onItemClickCallback: OnItemClickCallback? = null
//
//    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
//        this.onItemClickCallback = onItemClickCallback
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvItem.text = listReview[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
           // intent.putExtra(DetailActivity.EXTRA_NAME, "dicoding.com")
           // val jobId = position + 1
            //val url = "https://www.dicoding.com/$jobId"
            val url = "https://www.dicoding.com/"
            intent.putExtra(DetailActivity.EXTRA_URL, url)
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return listReview.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvItem: TextView = view.findViewById(R.id.tvItem)

    }

//        fun bind(item: String) {
//
//            tvItem.text = item
//            binding.root.setOnClickListener {
//                //onItemClickCallback?.onItemClicked(detail)
//            }
//        }




//    interface OnItemClickCallback {
//        fun onItemClicked(data: Detail)
//    }



}