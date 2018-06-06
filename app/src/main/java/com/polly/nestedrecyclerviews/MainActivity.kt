package com.polly.nestedrecyclerviews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
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

        val hordes = listOf(
                Horde(friendlyHorde),
                Horde(fightyHorde, hoistedToTopLevel = true),
                Horde(undeadHorde, hoistedToTopLevel = true))

        outer_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        outer_recycler_view.adapter = HordeAdapter(hordes)
    }
}

class HordeAdapter(hordes: List<Horde>) : RecyclerView.Adapter<RowViewHolder>() {

    private val hordeViewType = 1
    private val monsterViewType = 2

    private val rows: List<Row>
    init {
        val items = mutableListOf<Row>()
        hordes.forEach { horde ->
            if(horde.hoistedToTopLevel) horde.monsters.forEach { monster ->
                items.add(monster)
            } else {
                items.add(horde)
            }
        }
        rows = items.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        return when (viewType) {
            hordeViewType -> HordeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.horde, parent, false))
            monsterViewType -> MonsterCreator.create(parent)
            else -> { throw IllegalArgumentException()}
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (rows.get(position)) {
            is Horde -> hordeViewType
            is Monster -> monsterViewType
        }
    }

    override fun getItemCount(): Int = rows.size

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        when (holder) {
            is HordeViewHolder -> {
                val horde = rows[position] as Horde
                val context = holder.hordeRecyclerView.context
                val monsters = horde.monsters
                holder.hordeRecyclerView.adapter = MonsterAdapter(monsters)
                holder.hordeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
            is MonsterViewHolder -> {
                val monster = rows[position] as Monster
                MonsterBinder.bind(holder, monster)
            }
        }

    }

}

class MonsterAdapter(private val monsters: List<Monster>) : RecyclerView.Adapter<MonsterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        return MonsterCreator.create(parent)
    }

    override fun getItemCount(): Int {
        return monsters.size
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        MonsterBinder.bind(holder, monsters[position])
    }
}

object MonsterCreator {
    fun create(parent: ViewGroup) : MonsterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.monster, parent, false)
        return MonsterViewHolder(view)
    }
}

object MonsterBinder {
    fun bind(holder: MonsterViewHolder, monster: Monster) {
        holder.monsterNameText.text = monster.name
    }
}