package com.internship.data.repository

import com.internship.data.local.dao.CameraDao
import com.internship.data.local.dao.DoorDao
import com.internship.data.mapper.toCamera
import com.internship.data.mapper.toDoor
import com.internship.data.remote.ApiService
import com.internship.domain.model.Camera
import com.internship.domain.model.Door
import com.internship.domain.repository.InternRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import javax.inject.Inject

class InternRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val realm: Realm
) : InternRepository {

    override suspend fun getCams(fetchFromRemote: Boolean): List<Camera> {
        var camsFromDb = realm.query<CameraDao>().find()
        if (camsFromDb.isEmpty() || fetchFromRemote) {
            val remoteCamsData = apiService.getCams()?.data?.camera
            if (!remoteCamsData.isNullOrEmpty()) {
                realm.write {
                    remoteCamsData.forEach { cam ->
                        val camera = CameraDao().apply {
                            id = cam.id
                            name = cam.name
                            image = cam.snapshot
                            room = cam.room
                            isFavourite = cam.favorites
                            isRec = cam.rec
                        }
                        copyToRealm(instance = camera, updatePolicy = UpdatePolicy.ALL)
                    }
                }
                camsFromDb = realm.query<CameraDao>().find()
            }
        }
        return camsFromDb.map { it.toCamera() }
    }

    override suspend fun getDoors(fetchFromRemote: Boolean): List<Door> {
        var doorsFromDb = realm.query<DoorDao>().find()
        if (doorsFromDb.isEmpty() || fetchFromRemote) {
            val remoteDoorsData = apiService.getDoors()?.data
            if (!remoteDoorsData.isNullOrEmpty()) {
                realm.write {
                    remoteDoorsData.forEach { door ->
                        val newDoor = DoorDao().apply {
                            id = door.id
                            name = door.name
                            image = door.snapshot
                            room = door.room
                            isFavourite = door.favorites
                        }
                        copyToRealm(instance = newDoor, updatePolicy = UpdatePolicy.ALL)
                    }
                }
                doorsFromDb = realm.query<DoorDao>().find()
            }
        }
        return doorsFromDb.map { it.toDoor() }
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
}