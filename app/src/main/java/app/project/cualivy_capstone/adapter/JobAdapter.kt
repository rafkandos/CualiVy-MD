package app.project.cualivy_capstone.adapter

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


class JobAdapter(private val listJob: ArrayList<String>) : RecyclerView.Adapter<JobAdapter.ViewHolder>() {

    private var onItemClickListener: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_job, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = listJob[position]
        holder.bind(job)

//        holder.itemView.setOnClickListener {
//            onItemClickListener?.invoke(job)
//        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
//            intent.putExtra(DetailActivity.EXTRA_NAME, "dicoding.com")
////            val jobId = position + 1
////            val url = "https://www.dicoding.com/$jobId"
//            val url = "https://www.dicoding.com/"
//            intent.putExtra(DetailActivity.EXTRA_URL, "")
            PreferenceManager.saveGuid(JobData(job.substring(0, 36), ""))
            holder.itemView.context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return listJob.size
    }

    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvItem: TextView = view.findViewById(R.id.tvItem)

        fun bind(job: String) {
            tvItem.text = job


        }
    }
}
