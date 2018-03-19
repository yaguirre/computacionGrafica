using System.Collections.Generic;
using System;
using System.IO;
 
using UnityEngine;

public class BezierSurface : MonoBehaviour {

    public static double STEPS = 100;
    public static int numPointsControl;

    public static GameObject CreateSurface() {
        GameObject surface = new GameObject("mySurface");
        MeshFilter mf = surface.AddComponent(typeof(MeshFilter)) as MeshFilter;
        MeshRenderer mr = surface.AddComponent(typeof(MeshRenderer)) as MeshRenderer;
        Mesh mesh = new Mesh();

        Vector3[,] controlPoints = LeerArchivo("Assets\\Resources\\puntosControl.txt");
        List<Vector3> listaVertices = new List<Vector3>();
        Vector3 aux = new Vector3(0, 0, 0);

        double step = 1/STEPS;
        double u = 0;
        double v = 0;
        
        for (int i = 0; i < STEPS; i++){
            for (int j = 0; j < STEPS; j++){
                aux = BezierMethod(u, v, numPointsControl - 1, controlPoints);
                listaVertices.Add(new Vector3(aux.x,aux.y,aux.z));
                v += step;
            }
            v = 0;
            u += step;
        }

        Vector3[] verticesPoints = new Vector3[listaVertices.Count];
        int index = 0;
        foreach(Vector3 vertice in listaVertices){
            verticesPoints[index] = vertice;
            index++;
        }

        mesh.vertices = verticesPoints;

        List<int> verticesTriangulos = new List<int>();
        int vertice1 = 0;
        int vertice2 = vertice1 + Convert.ToInt32(STEPS);
        int vertice3 = 1;
        int vertice4 = vertice2 + 1;
        int auxSteps = Convert.ToInt32(STEPS) - 1;

        while (vertice4 < listaVertices.Count){
            //En el sentido antihorario
            verticesTriangulos.Add(vertice1);
            verticesTriangulos.Add(vertice2);
            verticesTriangulos.Add(vertice3);
            verticesTriangulos.Add(vertice3);
            verticesTriangulos.Add(vertice2);
            verticesTriangulos.Add(vertice4);

            //En el sentido horario
            verticesTriangulos.Add(vertice1);
            verticesTriangulos.Add(vertice3);
            verticesTriangulos.Add(vertice2);
            verticesTriangulos.Add(vertice2);
            verticesTriangulos.Add(vertice3);
            verticesTriangulos.Add(vertice4);

            vertice1++;
            vertice2++;
            vertice3++;
            vertice4++;
            if (vertice1 % auxSteps == 0){
                vertice1++;
                vertice2++;
                vertice3++;
                vertice4++;
                auxSteps += Convert.ToInt32(STEPS);
            }
        }

        int[] triangulos = new int[verticesTriangulos.Count];
        int pos = 0;
        foreach(int vertice in verticesTriangulos){
            triangulos[pos] = vertice;
            pos++;
        }

        mesh.triangles = triangulos;

        mf.mesh = mesh;
        mr.material = new Material(Shader.Find("Diffuse"));
        return surface;
    }

    static Vector3[,] LeerArchivo(string fileName){
        StreamReader reader = File.OpenText(fileName);
        List<Vector3> valores = new List<Vector3>();
        string line = reader.ReadLine();
        double Npuntos = Convert.ToDouble(line);
        numPointsControl = Convert.ToInt32(Math.Sqrt(Npuntos));
        int puntoActual = 0;

        while (puntoActual<Npuntos){
            line = reader.ReadLine();
            string[] coordenadas = line.Split(' ');
            float x = Convert.ToSingle(coordenadas[0]);
            float y = Convert.ToSingle(coordenadas[1]);
            float z = Convert.ToSingle(coordenadas[2]);
            valores.Add(new Vector3(x,y,z));
            puntoActual++;
        }

        Vector3[,] controlPoints = new Vector3[numPointsControl, numPointsControl];
        int posFila = 0;
        int posCol = 0;
        foreach(Vector3 punto in valores){
            controlPoints[posFila, posCol] = punto;
            posCol++;
            if (posCol == numPointsControl){
                posFila++;
                posCol = 0;
            }
            
        }
        return controlPoints;
    }

    static Vector3 BezierMethod(double u, double v,int n, Vector3[,] controlPoints){
        double acumX = 0;
        double acumY = 0;
        double acumZ = 0;
        for (int j = 0; j<= n; j++){
            for (int k = 0; k <=  n; k++){
                double blendU = Blending(u,n,j);
                double blendV = Blending(v, n, k);

                acumX += controlPoints[j, k].x * blendU * blendV;
                acumY += controlPoints[j, k].y * blendU * blendV;
                acumZ += controlPoints[j, k].z * blendU * blendV;
            }
        }
        return new Vector3(Convert.ToSingle(acumX), Convert.ToSingle(acumY), Convert.ToSingle(acumZ));
    }

    static double Blending(double t, double n, double k){
        double ret = Coeff(n, k) * Math.Pow(t, k) * Math.Pow(1 - t, n - k);
        return ret;
    }

    static double Coeff(double n, double k){
        double res = Fact(n) / (Fact(k) * Fact(n - k));
        return res;
    }
  
    static double Fact(double n){
        double acum = 1;
        for (double i = 1; i <= n; i++){
            acum *= i;
        }
        return acum;
    }
}
