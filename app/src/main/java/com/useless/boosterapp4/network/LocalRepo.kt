package com.useless.boosterapp4.network

import android.widget.ProgressBar
import com.useless.boosterapp4.ui.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LocalRepo {
    /*
        var seconds: Int = 0

        private val timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                seconds = (millisUntilFinished / 1000).toInt()
            }
            override fun onFinish() {
            }
        }
    */
    private val apiServices: ApiInterface by lazy {
        APIClient.getClient().create(ApiInterface::class.java)
    }

    private const val apiKey = "6637f7d017283c784ff6746c01f71453"
    private var lastUsedFun: Int = 4
    private lateinit var movieListData: MovieList
    private lateinit var prevMovieListData: MovieList
    private lateinit var movieData: MovieResponse
    private lateinit var listOfMovies: List<Movie>
    private lateinit var movie: Movie

    fun requestLastFun(
        callback: MovieListCallback,
        loadingBar: ProgressBar,
        page: Int,
        addInfo: Boolean
    ) {

        when (lastUsedFun) {
            0 -> requestMovieList(callback, page, addInfo)
            2 -> requestTopRatedMovieList(callback, page, addInfo)
        }
    }

    fun requestMovieList(callback: MovieListCallback, page: Int, addInfo: Boolean = false) {

        if (this::movieListData.isInitialized && lastUsedFun == 0 && !addInfo) {

            callback.onMovieListReady(listOfMovies)
            return
        }
        lastUsedFun = 0

        //   loadingBar.show()
        apiServices.doGetMoviesList(apiKey, page = page)
            .enqueue(object : Callback<MovieList> {

                override fun onResponse(
                    call: Call<MovieList>,
                    response: Response<MovieList>
                ) {
                    println("OnResponseCalled")
                    if (response.isSuccessful) {
                        if (!addInfo) {
                            movieListData = response.body()!!
                            listOfMovies = movieListData.list.map {
                                Movie(
                                    it.id, it.posterPath, it.lang,
                                    it.title, it.date, it.voteAvg,
                                    it.voteCnt, it.overview, movieListData.page,
                                    movieListData.totalPages
                                )
                            }
                        } else if (addInfo) {
                            prevMovieListData = movieListData
                            movieListData = response.body()!!
                            prevMovieListData.list.addAll(movieListData.list)
                            movieListData.list = prevMovieListData.list
                            listOfMovies = movieListData.list.map {
                                Movie(
                                    it.id, it.posterPath, it.lang,
                                    it.title, it.date, it.voteAvg,
                                    it.voteCnt, it.overview, movieListData.page,
                                    movieListData.totalPages
                                )
                            }
                        }
                        callback.onMovieListReady(listOfMovies)
                        //    loadingBar.hide()
                    } else if (response.code() in 400..404) {

                        val msg = "The movies didn't load properly from the API ${response.code()}"
                        callback.onMovieListError(msg)
                    }
                }

                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    t.printStackTrace()
                    val msg = "Error while getting movie data ${t.message}"
                    callback.onMovieListError(msg)
                }
            })

    }


    fun requestMovieData(callback: MovieCallback, movieID: Int) {
        if (this::movieData.isInitialized && lastUsedFun == 1) {
            movie = Movie(
                movieData.id,
                movieData.posterPath,
                movieData.lang,
                movieData.title,
                movieData.date,
                movieData.voteAvg,
                movieData.voteCnt,
                movieData.overview,
                movieListData.page,
                movieListData.totalPages
            )
            callback.onMovieReady(movie)
            return
        }
        lastUsedFun = 1
        apiServices.doGetMovieByID(movieID, apiKey)
            .enqueue(object : Callback<MovieResponse> {

                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    println("OnResponseCalled")
                    if (response.isSuccessful) {
                        movieData = response.body()!!
                        callback.onMovieReady(movie)
                    } else if (response.code() in 400..404) {
                        val msg = "The movies didn't load properly from the API"
                        callback.onMovieError(msg)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    t.printStackTrace()
                    val msg = "Error while getting movie data"
                    callback.onMovieError(msg)
                }
            })
    }

    fun requestTopRatedMovieList(callback: MovieListCallback, page: Int, addInfo: Boolean = false) {
        if (this::movieListData.isInitialized && lastUsedFun == 2 && !addInfo) {

            callback.onMovieListReady(listOfMovies)
            return
        }
        lastUsedFun = 2

        // loadingBar.show()
        apiServices.doGetMovieByRate(apiKey, page = page)
            .enqueue(object : Callback<MovieList> {

                override fun onResponse(
                    call: Call<MovieList>,
                    response: Response<MovieList>
                ) {
                    println("OnResponseCalled")
                    if (response.isSuccessful) {
                        if (!addInfo) {
                            movieListData = response.body()!!
                            listOfMovies = movieListData.list.map {
                                Movie(
                                    it.id, it.posterPath, it.lang,
                                    it.title, it.date, it.voteAvg,
                                    it.voteCnt, it.overview, movieListData.page,
                                    movieListData.totalPages
                                )
                            }
                        } else if (addInfo) {
                            prevMovieListData = movieListData
                            movieListData = response.body()!!
                            prevMovieListData.list.addAll(movieListData.list)
                            movieListData.list = prevMovieListData.list
                            listOfMovies = movieListData.list.map {
                                Movie(
                                    it.id, it.posterPath, it.lang,
                                    it.title, it.date, it.voteAvg,
                                    it.voteCnt, it.overview, movieListData.page,
                                    movieListData.totalPages
                                )
                            }
                        }
                        callback.onMovieListReady(listOfMovies)
                        //   loadingBar.hide()
                    } else if (response.code() in 400..404) {

                        val msg = "The movies didn't load properly from the API ${response.code()}"
                        callback.onMovieListError(msg)
                    }
                }

                override fun onFailure(call: Call<MovieList>, t: Throwable) {
                    t.printStackTrace()
                    val msg = "Error while getting movie data ${t.message}"
                    callback.onMovieListError(msg)
                }


            })

    }


    interface MovieListCallback {
        fun onMovieListReady(movieData: List<Movie>)
        fun onMovieListError(errorMsg: String)
    }

    interface MovieCallback {
        fun onMovieReady(movieData: Movie)
        fun onMovieError(errorMsg: String)
    }
}