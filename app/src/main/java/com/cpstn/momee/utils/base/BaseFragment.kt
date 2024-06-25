package com.cpstn.momee.utils.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(private val bindingInflater: Inflate<VB>) : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    protected val safetyViewBinding get() = _binding

    lateinit var mContext: Context

    companion object {
        fun newInstanceFragment(bundle: Bundle, fragment: Fragment): Fragment {
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBundleData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
    }

    open fun setBundleData() { }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setupView()

    @Suppress("UNCHECKED_CAST")
    fun <T : Fragment> getFragment(fragmentId: Int): T? {
        return childFragmentManager.findFragmentById(fragmentId) as? T
    }

    fun addChildFragment(frameId: Int, fragment: Fragment) {
        childFragmentManager.inTransaction { add(frameId, fragment) }
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    fun Fragment.checkRequireArguments(key: String, shouldRemoveKey: Boolean = false, doOnNext: (String) -> Unit) {
        checkRequireArguments(arguments, key, shouldRemoveKey, doOnNext)
    }

    fun Fragment.checkRequireArguments(bundle: Bundle?, key: String, shouldRemoveKey: Boolean = false, doOnNext: (String) -> Unit) {
        if (bundle?.containsKey(key) == true) {
            doOnNext(key)
            if (shouldRemoveKey) arguments?.remove(key)
        }
    }

    private fun Fragment.replaceChildFragment(containerViewId: Int, fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(containerViewId, fragment).commit()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T : Fragment> Fragment.getFragment(fragmentId: Int): T? {
        return childFragmentManager.findFragmentById(fragmentId) as? T
    }
}
