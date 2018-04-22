package com.polly.nestedrecyclerviews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.horde.view.*
import kotlinx.android.synthetic.main.monster.view.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val friendlyHorde = listOf<Monster>(
                Monster("Big blue monster"),
                Monster("Terence"),
                Monster("Fluffy"))

        val fightyHorde = listOf<Monster>(
                Monster("Medusa"),
                Monster("Basilisk"),
                Monster("Manticore"))

        val undeadHorde = listOf<Monster>(
                Monster("Dracula"),
                Monster("Zombie brain eater"),
                Monster("Spike"))

        val hordes = listOf<Horde>(
                Horde(friendlyHorde),
                Horde(fightyHorde),
                Horde(undeadHorde, true))

        outer_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        outer_recycler_view.adapter = HordeAdapter(hordes)
    }
}

class HordeAdapter(private val hordes: List<Horde>) : RecyclerView.Adapter<RowViewHolder>() {

    private val hordeViewType = 1
    private val monsterViewType = 2

    private val rowTypes: List<RowType>
    init {
        val items = mutableListOf<RowType>()
        hordes.forEach { horde ->
            if(horde.embedded) horde.monsters.forEach { monster ->
                items.add(monster)
            } else {
                items.add(horde)
            }
        }
        rowTypes = items.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val viewHolder = when (viewType) {
            hordeViewType -> HordeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.horde, parent, false))
            monsterViewType -> MonsterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.monster, parent, false))
            else -> { throw IllegalArgumentException()}
        }
        return viewHolder
    }

    override fun getItemViewType(position: Int): Int {
        return when (rowTypes.get(position)) {
            is Horde -> hordeViewType
            is Monster -> monsterViewType
        }
    }

    override fun getItemCount(): Int = rowTypes.size

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        when (holder) {
            is HordeViewHolder -> {
                val horde = rowTypes[position] as Horde
                val context = holder.hordeRecyclerView.context
                val monsters = horde.monsters
                holder.hordeRecyclerView.adapter = MonsterAdapter(monsters)
                holder.hordeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            is MonsterViewHolder -> {
                val monster = rowTypes[position] as Monster
                holder.monsterNameText.text = monster.name
            }
        }

    }

}

sealed class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class HordeViewHolder(itemView: View) : RowViewHolder(itemView) {

    val hordeRecyclerView : RecyclerView = itemView.horde_recycler_view
}

class MonsterViewHolder(itemView: View) : RowViewHolder(itemView) {

    val monsterNameText: TextView = itemView.monster_name
}

class MonsterAdapter(private val monsters: List<Monster>) : RecyclerView.Adapter<MonsterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.monster, parent, false)
        return MonsterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return monsters.size
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        holder.monsterNameText.text = monsters[position].name
    }

}
