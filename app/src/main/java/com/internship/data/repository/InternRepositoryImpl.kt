package com.internship.data.repository

import com.internship.data.local.dao.CameraDao
import com.internship.data.local.dao.DoorDao
import com.internship.data.local.dao.RoomDao
import com.internship.data.mapper.toCamera
import com.internship.data.mapper.toCameraDao
import com.internship.data.mapper.toDoor
import com.internship.data.mapper.toDoorDao
import com.internship.data.mapper.toRoomTitle
import com.internship.data.remote.ApiService
import com.internship.domain.model.Camera
import com.internship.domain.model.Door
import com.internship.domain.repository.InternRepository
import com.internship.utils.Resource
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import javax.inject.Inject

class InternRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val realm: Realm
) : InternRepository {

    private var roomCache = mutableListOf<String>()

    override suspend fun getCams(fetchFromRemote: Boolean): Resource<List<Camera>> {
        var camsFromDb = realm.query<CameraDao>().find()
        if (camsFromDb.isEmpty() || fetchFromRemote) {
            val remoteData = apiService.getCams()
            when (remoteData) {
                is Resource.Error -> {
                    return Resource.Error(message = remoteData.message ?: ERROR_MESSAGE)
                }

                is Resource.Success -> {
                    remoteData.data?.data?.room?.let {
                        roomCache.clear()
                        roomCache.addAll(it)
                    }
                    val remoteCamsData = remoteData.data?.data?.camera
                    if (!remoteCamsData.isNullOrEmpty()) {
                        realm.write {
                            remoteCamsData.forEach { cam ->
                                val camera = cam.toCameraDao()
                                copyToRealm(instance = camera, updatePolicy = UpdatePolicy.ALL)
                            }
                        }
                        camsFromDb = realm.query<CameraDao>().find()
                    }
                }
            }
        }
        val result = camsFromDb.map { it.toCamera() }
        return Resource.Success(data = result)
    }

    override suspend fun getDoors(fetchFromRemote: Boolean): Resource<List<Door>> {
        var doorsFromDb = realm.query<DoorDao>().find()
        if (doorsFromDb.isEmpty() || fetchFromRemote) {
            val remoteData = apiService.getDoors()
            when (remoteData) {
                is Resource.Error -> {
                    return Resource.Error(message = remoteData.message ?: ERROR_MESSAGE)
                }

                is Resource.Success -> {
                    val remoteDoorsData = remoteData.data?.data
                    if (!remoteDoorsData.isNullOrEmpty()) {
                        realm.write {
                            remoteDoorsData.forEach { door ->
                                val newDoor = door.toDoorDao()
                                copyToRealm(instance = newDoor, updatePolicy = UpdatePolicy.ALL)
                            }
                        }
                        doorsFromDb = realm.query<DoorDao>().find()
                    }
                }
            }
        }
        val result = doorsFromDb.map { it.toDoor() }
        return Resource.Success(data = result)
    }

    override suspend fun getRooms(fetchFromRemote: Boolean): List<String> {
        var roomsFromDb = realm.query<RoomDao>().find()

        if (roomsFromDb.isEmpty() && roomCache.isNotEmpty()) {
            realm.write {
                roomCache.forEachIndexed { index, room ->
                    val newRoom = RoomDao().apply {
                        id = index
                        title = room
                    }
                    copyToRealm(instance = newRoom, updatePolicy = UpdatePolicy.ALL)
                }
            }
            roomsFromDb = realm.query<RoomDao>().find()
        }

        if (roomsFromDb.isEmpty() || fetchFromRemote && roomCache.isEmpty()) {
            val remoteData = apiService.getCams()
            when (remoteData) {
                is Resource.Error -> Unit

                is Resource.Success -> {
                    val remoteRooms = remoteData.data?.data?.room
                    if (!remoteRooms.isNullOrEmpty()) {
                        realm.write {
                            remoteRooms.forEachIndexed { index, room ->
                                val newRoom = RoomDao().apply {
                                    id = index
                                    title = room
                                }
                                copyToRealm(instance = newRoom, updatePolicy = UpdatePolicy.ALL)
                            }
                        }
                        roomsFromDb = realm.query<RoomDao>().find()
                    }
                }
            }
        }

        val result = roomsFromDb.map { it.toRoomTitle() }
        return result
    }

    override suspend fun setFavouriteCam(camId: Int, favourite: Boolean) {
        val cam = realm.query<CameraDao>("id == $0", camId).find().first()
        realm.write {
            findLatest(cam)?.let { camera ->
                camera.isFavourite = favourite
            }
        }
    }

    override suspend fun setFavouriteDoor(doorId: Int, favourite: Boolean) {
        val existingDoor = realm.query<DoorDao>("id == $0", doorId).find().first()
        realm.write {
            findLatest(existingDoor)?.let { door ->
                door.isFavourite = favourite
            }
        }
    }

    override suspend fun setLockToDoor(doorId: Int, lock: Boolean) {
        val existingDoor = realm.query<DoorDao>("id == $0", doorId).find().first()
        realm.write {
            findLatest(existingDoor)?.let { door ->
                door.isOpened = lock
            }
        }
    }

    override suspend fun editDoorName(doorId: Int, newName: String) {
        val existingDoor = realm.query<DoorDao>("id == $0", doorId).find().first()
        realm.write {
            findLatest(existingDoor)?.let { door ->
                door.name = newName
            }
        }
    }

    companion object {
        private const val ERROR_MESSAGE = "Something bad happened"
    }
}