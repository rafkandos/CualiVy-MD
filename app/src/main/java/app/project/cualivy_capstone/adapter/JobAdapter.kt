package app.project.cualivy_capstone.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import app.project.cualivy_capstone.R
import app.project.cualivy_capstone.detail.DetailActivity
import app.project.cualivy_capstone.preference.PreferenceManager
import app.project.cualivy_capstone.response.Detail
import app.project.cualivy_capstone.response.JobData
import com.bumptech.glide.Glide
import org.json.JSONObject



class JobAdapter(private val listJob: ArrayList<JSONObject>) :
    RecyclerView.Adapter<JobAdapter.ViewHolder>() {

    private var onItemClickListener: ((JSONObject) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = listJob[position]
        holder.bind(job)

        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(job)
       val intent = Intent(holder.itemView.context, DetailActivity::class.java)
//          PreferenceManager.saveGuid(JobData(job.substring(0, 36), ""))
        val jobData = JobData(guid = job.getString("guid"), link = "")
          PreferenceManager.saveGuid(jobData)
          holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listJob.size
    }

    fun setOnItemClickListener(listener: (JSONObject) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvItem: TextView = view.findViewById(R.id.tvItem)
        private val tvCompany: TextView = view.findViewById(R.id.tv_company)
        private val tvLocation: TextView = view.findViewById(R.id.tv_location)
        private val imgUser: ImageView = view.findViewById(R.id.img_user)

        fun bind(job: JSONObject) {
            val position = job.getString("position")
            val company = job.getString("companyname")
            val location = job.getString("location")
            val image = job.getString("image")

            tvItem.text = position
            tvCompany.text = company
            tvLocation.text = location

            Glide.with(itemView)
                .load(image)
                .into(imgUser)
        }
    }
}

//
//class JobAdapter(private val listJob: ArrayList<JSONObject>) : RecyclerView.Adapter<JobAdapter.ViewHolder>() {
//
//    private var onItemClickListener: ((JSONObject) -> Unit)? = null
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val job = listJob[position]
//        holder.bind(job)
//
////        holder.itemView.setOnClickListener {
////            onItemClickListener?.invoke(job)
////        }
//
//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
////            PreferenceManager.saveGuid(JobData(job.substring(0, 36), ""))
//            val jobData = JobData(guid = job.getString("guid"), link = "")
//            PreferenceManager.saveGuid(jobData)
//            holder.itemView.context.startActivity(intent)
//
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return listJob.size
//    }
//
//    fun setOnItemClickListener(listener: (JSONObject) -> Unit) {
//        onItemClickListener = listener
//    }
//
//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
////        private val tvItem: TextView = view.findViewById(R.id.tvItem)
//        private val tvPosition: TextView = view.findViewById(R.id.tv_position)
//        private val tvCompany: TextView = view.findViewById(R.id.tv_company)
//        private val tvLocation: TextView = view.findViewById(R.id.tv_location)
//        private val imgUser: ImageView = view.findViewById(R.id.img_user)
//
//
//        fun bind(job: JSONObject) {
//            val position = job.getString("position")
//            val company = job.getString("companyname")
//            val location = job.getString("location")
//            val image = job.getString("image")
//
//            tvPosition.text = position
//            tvCompany.text = company
//            tvLocation.text = location
//
//            Glide.with(itemView)
//                .load(image)
//                .into(imgUser)
//
//
//
//        }
//    }
//}
