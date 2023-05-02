package com.erhansen.githubprofileviewer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.erhansen.githubprofileviewer.R
import com.erhansen.githubprofileviewer.model.RepositoriesModel
import de.hdodenhof.circleimageview.CircleImageView

class RepositoriesAdapter(private val repositories: RepositoriesModel) : RecyclerView.Adapter<RepositoriesAdapter.ItemHolder>(){

    class ItemHolder(itemView: View) : ViewHolder(itemView) {
        val repoName: TextView = itemView.findViewById(R.id.repoName)
        val descriptionText: TextView = itemView.findViewById(R.id.descriptionText)
        val languageColor: CircleImageView = itemView.findViewById(R.id.languageColor)
        val languageText: TextView = itemView.findViewById(R.id.languageText)
        val starText: TextView = itemView.findViewById(R.id.starText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repositories, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.repoName.text = repositories[position].name
        holder.descriptionText.text = repositories[position].description
        holder.languageText.text = repositories[position].language
        holder.starText.text = repositories[position].stargazersCount.toString()
    }


}