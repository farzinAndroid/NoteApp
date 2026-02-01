package com.farzin.noteappmvp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import com.example.local.Constants
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ui.R
import com.farzin.noteappmvp.data.models.NoteEntity
import com.farzin.noteappmvp.databinding.NoteItemBinding
import javax.inject.Inject

class NotesListAdapter @Inject constructor() : RecyclerView.Adapter<NotesListAdapter.MyViewHolder>()  {

    private lateinit var binding: NoteItemBinding
    private lateinit var context: Context
    private var notesList = emptyList<NoteEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return MyViewHolder()
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(notesList[position])
        holder.setIsRecyclable(true)
    }


    inner class MyViewHolder() : RecyclerView.ViewHolder(binding.root){
        fun bind(item:NoteEntity){
            binding.apply {

                /*root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }*/

                descTxt.text = item.desc
                titleTxt.text = item.title
                when(item.priority){
                    Constants.HIGH->{
                        priorityColor.setBackgroundColor(context.getColor(R.color.red))
                    }
                    Constants.MEDIUM->{
                        priorityColor.setBackgroundColor(context.getColor(R.color.yellow))
                    }
                    Constants.LOW->{
                        priorityColor.setBackgroundColor(context.getColor(R.color.aqua))
                    }
                }

                when(item.category){
                    Constants.HEALTH->{
                        categoryImg.setImageResource(com.example.ui.R.drawable.healthcare)
                    }
                    Constants.HOME->{
                        categoryImg.setImageResource(com.example.ui.R.drawable.home)
                    }
                    Constants.WORK->{
                        categoryImg.setImageResource(com.example.ui.R.drawable.work)
                    }
                    Constants.EDUCATION->{
                        categoryImg.setImageResource(com.example.ui.R.drawable.education)
                    }

                }


                menuImg.setOnClickListener {
                    val popUpMenu = PopupMenu(context,it)
                    popUpMenu.inflate(com.farzin.noteappmvp.R.menu.menu_item)
                    popUpMenu.show()

                    //click
                    popUpMenu.setOnMenuItemClickListener {menuItem->

                        when(menuItem.itemId){
                            com.farzin.noteappmvp.R.id.item_delete->{
                                onItemClickListener?.let { it1 -> it1(item,Constants.DELETE) }
                            }
                            com.farzin.noteappmvp.R.id.item_edit->{
                                onItemClickListener?.let { it1 -> it1(item,Constants.EDIT) }
                            }
                        }

                        return@setOnMenuItemClickListener true
                    }
                }

            }
        }
    }


    private var onItemClickListener : ((NoteEntity,String) ->  Unit)? = null

    fun setOnClickListener(listener:((NoteEntity,String) ->  Unit)) {
        onItemClickListener = listener
    }

    fun setData(data:List<NoteEntity>){
        val notesDiffUtils = NotesDiffUtils(notesList,data)
        val diffUtils = DiffUtil.calculateDiff(notesDiffUtils)
        notesList = data
        diffUtils.dispatchUpdatesTo(this)
    }


    class NotesDiffUtils(private val oldItem:List<NoteEntity>,private val newItem:List<NoteEntity>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] == newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] == newItem[newItemPosition]
        }
    }



}