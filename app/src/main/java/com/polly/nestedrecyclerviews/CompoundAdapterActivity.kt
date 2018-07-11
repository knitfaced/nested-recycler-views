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
                Horde(fightyHorde, name = "fighty horde"),
                Horde(undeadHorde, name = "undead horde"))

        outer_recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val adapter = CompoundAdapter()
        adapter.registerAdapter(EmbeddedAdapter(hordes) as RecyclerView.Adapter<RowViewHolder>)
        adapter.registerAdapter(CompoundMonsterAdapter(undeadHorde) as RecyclerView.Adapter<RowViewHolder>)
        outer_recycler_view.adapter = adapter
    }
}

class EmbeddedAdapter(private val hordes: List<Horde>) : RecyclerView.Adapter<HordeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HordeViewHolder {
        return HordeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.horde, parent, false))
    }

    override fun getItemCount(): Int {
        return hordes.size
    }

    override fun onBindViewHolder(holder: HordeViewHolder, position: Int) {
        val context = holder.hordeRecyclerView.context
        val monsters = hordes[position].monsters
        holder.hordeRecyclerView.adapter = MonsterAdapter(monsters)
        holder.hordeRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

}

class CompoundMonsterAdapter(private val monsters: List<Monster>) : RecyclerView.Adapter<MonsterViewHolder>() {

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

class CompoundAdapter : RecyclerView.Adapter<RowViewHolder>() {

    private val adapters = mutableListOf<RecyclerView.Adapter<RowViewHolder>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        return adapters[viewType].onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return adapters.fold(0) { total, next -> total + next.itemCount }
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        var startIndex = 0
        adapters.forEach {
            val endIndex = startIndex + it.itemCount
            if (position in startIndex until endIndex) {
                it.onBindViewHolder(holder, position - startIndex)
                return
            }
            startIndex = endIndex
        }
    }

    override fun getItemViewType(position: Int): Int {
        var startIndex = 0
        adapters.forEachIndexed { index, adapter ->
            val endIndex = startIndex + adapter.itemCount
            if (position in startIndex until endIndex) {
                return index
            }
            startIndex = endIndex
        }
        return 0
    }

    fun registerAdapter(adapter: RecyclerView.Adapter<RowViewHolder>) {
        adapters.add(adapter)
    }

}
