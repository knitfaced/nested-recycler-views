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
                Horde(undeadHorde))

        outer_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        outer_recycler_view.adapter = HordeAdapter(hordes)
    }
}

data class Horde(val monsters: List<Monster>, val embedded: Boolean = false)

class HordeAdapter(val hordes: List<Horde>) : RecyclerView.Adapter<HordeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HordeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.horde, parent, false)
        return HordeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hordes.size
    }

    override fun onBindViewHolder(holder: HordeViewHolder, position: Int) {
        val monsters = hordes[position].monsters
        val context = holder.hordeRecyclerView.context
        holder.hordeRecyclerView.adapter = MonsterAdapter(monsters)
        holder.hordeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

}

class HordeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val hordeRecyclerView : RecyclerView = itemView.horde_recycler_view
}


class MonsterAdapter(val monsters: List<Monster>) : RecyclerView.Adapter<MonsterViewHolder>() {
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

class MonsterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val monsterNameText: TextView = itemView.monster_name
}
