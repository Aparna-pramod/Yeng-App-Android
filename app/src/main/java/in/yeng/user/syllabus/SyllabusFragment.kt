package `in`.yeng.user.syllabus

import `in`.yeng.user.R
import `in`.yeng.user.helpers.viewbinders.BinderSection
import `in`.yeng.user.helpers.viewbinders.BinderTypes
import `in`.yeng.user.home.MainActivity
import `in`.yeng.user.syllabus.helpers.SyllabusAdapter
import `in`.yeng.user.syllabus.helpers.SyllabusFilesAdapter
import `in`.yeng.user.syllabus.network.SyllabusAPI
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.satorufujiwara.binder.recycler.RecyclerBinderAdapter

class SyllabusFragment : Fragment() {

    companion object {
        val TAG: String = this::class.java.simpleName
    }

    private var _context: Context? = null

    lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        _context = context
    }

    override fun onDetach() {
        super.onDetach()
        _context = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.syllabus_fragment, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.loadingIndicator.smoothToShow()

        recyclerView = view.findViewById(R.id.recycler_view)

        val layoutManager = GridLayoutManager(_context, 3, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        val id = arguments?.getString("id") ?: "Syllabus"

        val adapter = RecyclerBinderAdapter<BinderSection, BinderTypes>()

        SyllabusAPI.getSyllabusList(id) { items, files ->
            _context?.let {

                recyclerView.adapter = adapter

                for (item in items)
                    adapter.add(BinderSection.SECTION_1, SyllabusAdapter(it as AppCompatActivity, item, id))

                for (item in files)
                    adapter.add(BinderSection.SECTION_1, SyllabusFilesAdapter(it as AppCompatActivity, item, id))

                MainActivity.loadingIndicator.smoothToHide()
            }

        }


    }

}