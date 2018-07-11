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
                Horde(friendlyHorde, name = "friendly horde"),
                Horde(fightyHorde, hoistedToTopLevel = true, name = "fighty horde"),
                Horde(undeadHorde, hoistedToTopLevel = true, name = "undead horde"))

        outer_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


//        val adapter = CompoundAdapter()
//        adapter.registerAdapter(ChildHordeAdapter(hordes))
//        adapter.registerAdapter(ChildMonsterAdapter(undeadHorde))
        outer_recycler_view.adapter = ChildHordeAdapter(hordes)
    }
}

class ChildHordeAdapter(private val hordes: List<Horde>) : RecyclerView.Adapter<MonsterViewHolder>() {

    val TITLE_ROW = 0
    val NORMAL_ROW = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonsterViewHolder {

        val layout = when (viewType) {
            TITLE_ROW -> R.layout.titled_monster
            else -> R.layout.monster
        }
        return MonsterViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    override fun getItemCount(): Int {
        return hordes.flatMap { it.monsters }.size
    }

    override fun onBindViewHolder(holder: MonsterViewHolder, position: Int) {
        val monster = hordes.flatMap { it.monsters }[position]
        holder.monsterNameText.text = monster.name

        var horde: Horde? = null
        var flatMapIndex = 0
        hordes.forEach { currentHorde ->
            currentHorde.monsters.forEachIndexed { index, monster ->
                if (flatMapIndex == position) {
                    horde = currentHorde
                }
                flatMapIndex++
            }
        }

        holder.titleText?.text = horde?.name
    }

    override fun getItemViewType(position: Int): Int {
        return if (isTitleRow(position)) TITLE_ROW else NORMAL_ROW
    }

    private fun isTitleRow(position: Int): Boolean {
        var flatMapIndex = 0
        var isTitleRow = false
        hordes.forEach {
            it.monsters.forEachIndexed { index, monster ->
                if (index == 0 && flatMapIndex == position) {
                    isTitleRow = true
                    return@forEach
                }
                flatMapIndex++
            }
        }
        return isTitleRow
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
