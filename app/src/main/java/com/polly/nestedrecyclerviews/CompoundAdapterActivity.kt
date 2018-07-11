package com.polly.nestedrecyclerviews

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*


class CompoundAdapterActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val friendlyHorde = listOf(
                Monster("Big blue monster"),
                Monster("Terence"),
                Monster("Fluffy"))

        val fightyHorde = listOf(
                Monster("Medusa"),
                Monster("Basilisk"),
                Monster("Manticore"))

        val undeadHorde = listOf(
                Monster("Dracula"),
                Monster("Zombie brain eater"),
                Monster("Spike"))

        val hordes = listOf(
                Horde(friendlyHorde),
                Horde(fightyHorde, hoistedToTopLevel = true),
                Horde(undeadHorde, hoistedToTopLevel = true))

        outer_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


//        val adapter = CompoundAdapter()
//        adapter.registerAdapter(ChildHordeAdapter(hordes))
//        adapter.registerAdapter(ChildMonsterAdapter(undeadHorde))
        outer_recycler_view.adapter = ChildHordeAdapter(hordes)
    }
}

class ChildHordeAdapter(private val hordes: List<Horde>) : RecyclerView.Adapter<MonsterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        return MonsterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.monster, parent, false))
    }

    override fun getItemCount(): Int {
        return hordes.flatMap { it.monsters }.size
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        val monster = hordes.flatMap { it.monsters }[position]
        holder.monsterNameText.text = monster.name
    }
}

class ChildMonsterAdapter(private val monsters: List<Monster>) : RecyclerView.Adapter<MonsterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {
        return MonsterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.monster, parent, false))
    }

    override fun getItemCount(): Int {
        return monsters.size
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        holder.monsterNameText.text = monsters[position].name
    }
}
//
//class CompoundAdapter : RecyclerView.Adapter<RowViewHolder>() {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
//
//    }
//
//    override fun getItemCount(): Int {
//
//    }
//
//    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
//
//    }
//
//}
