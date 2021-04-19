package com.nerdz.selfieapp

import android.graphics.Bitmap
import android.graphics.RectF
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.nerdz.selfieapp.utils.createBitmap
import com.nerdz.selfielib.ml.Detection
import com.nerdz.selfielib.screens.camera_screen.SelfieViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstrumentedFaceDetectionTest {

    @Rule
    @JvmField var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel: SelfieViewModel = SelfieViewModel()
    private val context = InstrumentationRegistry.getInstrumentation().context

    private val realTimeOpts = FaceDetectorOptions.Builder()
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()

    private val detector = FaceDetection.getClient(realTimeOpts)
    private val faceRectIn = RectF(0f, 20f, 50f, 80f)
    private val faceRectOut = RectF(0f, 5f, 50f, 80f)
    private val ovalOverlayRect = RectF(0f, 10f, 150f, 100f)

    @Test
    fun testFaceLandmarks_eyes_closed_smiling_face_in_position() {
        val bitmap = createBitmap(context, "face_images/smiling_eyes_closed.bmp")
        val rotationDegrees = 0
        val detection = getDetectionFromBitmap(bitmap, rotationDegrees)

        viewModel.processDetections(ovalOverlayRect, faceRectIn, detection)
        val instruction = viewModel.instructions.value

        assertEquals(instruction?.areEyesOpen, false)
        assertEquals(instruction?.isSmiling, true)
        assertEquals(instruction?.isFaceOnPosition, true)
    }

    @Test
    fun testFaceLandmarks_eyes_open_not_smiling_face_not_in_position() {
        val bitmap = createBitmap(context, "face_images/eyes_open_not_smiling.jpeg")
        val rotationDegrees = 0
        val detection = getDetectionFromBitmap(bitmap, rotationDegrees)

        viewModel.processDetections(ovalOverlayRect, faceRectOut, detection)
        val instruction = viewModel.instructions.value

        assertEquals(instruction?.areEyesOpen, true)
        assertEquals(instruction?.isSmiling, false)
        assertEquals(instruction?.isFaceOnPosition, false)
    }

    @Test
    fun testFaceLandmarks_eyes_open_smiling_face_in_position() {
        val bitmap = createBitmap(context, "face_images/smiling_eyes_open.bmp")
        val rotationDegrees = 0
        val detection = getDetectionFromBitmap(bitmap, rotationDegrees)

        viewModel.processDetections(ovalOverlayRect, faceRectIn, detection)
        val instruction = viewModel.instructions.value

        assertEquals(instruction?.areEyesOpen, true)
        assertEquals(instruction?.isSmiling, true)
        assertEquals(instruction?.isFaceOnPosition, true)
    }

    private fun getDetectionFromBitmap(bitmap: Bitmap, rotation: Int) : Detection {
        val inputImage = InputImage.fromBitmap(bitmap, rotation)

        val faces = Tasks.await(detector.process(inputImage))
        val detectedFace = faces[0]

        return Detection(detectedFace, rotation, bitmap.width, bitmap.height)
    }
}