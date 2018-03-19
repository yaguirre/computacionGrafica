using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CreateSurface : MonoBehaviour {

    public GameObject myCamera;
    private GameObject mySurface;
    // Use this for initialization

    void Start () {
        mySurface = BezierSurface.CreateSurface();
    }

    // Update is called once per frame
    void Update () {
        myCamera.transform.LookAt(mySurface.transform);
    }
}
