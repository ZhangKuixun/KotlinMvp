package com.hazz.kotlinmvp

import android.util.Log
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import java.lang.ref.WeakReference
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class ByTest {

    /**
     * 类的代理
     */
    // 定义一个接口,和一个方法 show()
    interface Base {
        fun show()
    }

    // 定义类实现 Base 接口, 并实现 show 方法
    open class BaseImpl : Base {
        override fun show() {
            Log.e("kevin", "BaseImpl::show()")
        }
    }

    // 定义代理类实现 Base 接口, 构造函数参数是一个 Base 对象
    // by 后跟 Base 对象, 不需要再实现 show()
    class BaseProxy(base: Base) : Base by base {
        fun showOther() {
            Log.e("kevin", "BaseImpl::showOther()")
        }
    }

    // main 方法
    fun mainGo() {
        val base = BaseImpl()
        BaseProxy(base).show()
        BaseProxy(base).showOther()
    }

    /**
     * 属性延迟加载
     * lzay 后跟的表达式，表达式的返回值必须和属性类型一致
     */
//    private val user: User by lazy {
//        ShareSparse.getValueBy(ShareSparse.USER_CLS) as User
//    }
    private lateinit var sex: String


    /**
     * 可观察属性
     */
    // main 方法
    fun mainGo1() {
        name = "dddd"
        name = "hhhh"
    }

    // 观察属性
    var name: String by Delegates.observable("hello",
            { kProperty: KProperty<*>,
              oldName: String,
              newName: String ->
                Log.e("kevin", "${kProperty.name}---${oldName}--${newName}")
            }
    )


    /**
     * 属性非空强校验
     */
    class User {
        var name: String by Delegates.notNull()
        fun init(name: String) {
            this.name = name
        }
    }

    fun main(args: Array<String>) {
        val user = User()
        // print(user.name)
        // user.name -> IllegalStateException
        user.init("Carl")
        println(user.name)
    }


    /**
     * 自定义监听属性变化
     */
    //自定义委托属性
    class A() {
        // 运算符重载
        operator fun getValue(thisRef: Any?, prop: KProperty<*>): String {
            return "$thisRef, thank you for delegating '${prop.name}' to me!"
        }

        operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: String) {
            println("$value has been assigned to ${prop.name} in $thisRef")
        }
    }

    // main 方法
    fun mainGo2() {
        var name: String by A()
        name = "aaaa"
        Log.e("kevin", name)
    }


    /**
     *
     *Describe:弱引用封装类 -kotlin
     *
     * 弱引用对象处理，防止移动端内存泄露
     */
    class Weak<T : Any>(initializer: () -> T?) {
        var weakReference = WeakReference<T?>(initializer())

        constructor() : this({
            null
        })

        operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
            return weakReference.get()
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            weakReference = WeakReference(value)
        }

    }


    /**
     * Map值 映射到类属性
     */
    class UserX(map: Map<String, Any?>) {
        val name: String by map
        val age: Int by map
    }

    fun main1(args: Array<String>) {
        val user = UserX(mapOf(
                "name" to "John Doe",
                "age" to 123
        ))

        // key 不存在报错  Key age is missing in the map.
        // 类型不一致 java.lang.ClassCastException: java.lang.String cannot be cast to java.lang.Number
        println("name = ${user.name}, age = ${user.age}")
    }


}
