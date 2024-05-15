package com.acme.kotlinintervals

class SecondaryActivity

/*
import com.acme.kotlinintervals.service.PlayerService

class SecondaryActivity : AppCompatActivity(), FirstFragment.OnProgramSelectedListener{
    private lateinit var mService: PlayerService
    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to service, cast the IBinder and get service instance
            val binder = service as PlayerService.LocalBinder
            mService = binder.getService()
            mService.setup()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {}
    }

    override fun onProgramSelected(item: Int) {
        Log.i("playTAG", "selected $item")
        mService.playProgram(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setSupportActionBar(binding.toolbar)
//
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

        // Bind to LocalService
        Intent(this, PlayerService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }
}

 */