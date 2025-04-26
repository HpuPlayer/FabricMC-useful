package top.ioart.items.tools;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemIdeaTest extends Item {
    public ItemIdeaTest(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        Vec3d start = user.getCameraPosVec(1.0F);
        Vec3d end = start.add(user.getRotationVec(1.0F).multiply(10));
        List<double[]> doubles = generateCircleOnLine(start.x, start.y, start.z,
                end.x, end.y, end.z,
                4, 180);
        for (double[] point : doubles) {
            world.addParticleClient(ParticleTypes.FLAME,true,true,point[0],point[1],point[2],0,0,0);
        }
        List<double[]> doubles1 = generateTriangleEdges(start.x, start.y, start.z,
                end.x, end.y, end.z,
                7, 30, false);
        for (double[] point : doubles1) {
            world.addParticleClient(ParticleTypes.FLAME,true,true,point[0],point[1],point[2],0,0,0);
        }
        List<double[]> doubles2 = generateTriangleEdges(start.x, start.y, start.z,
                end.x, end.y, end.z,
                7, 30,true);
        for (double[] point : doubles2) {
            world.addParticleClient(ParticleTypes.FLAME,true,true,point[0],point[1],point[2],0,0,0);
        }
        return super.use(world, user, hand);
    }

    public static List<double[]> generateCircleOnLine(double x1, double y1, double z1,
                                                      double x2, double y2, double z2,
                                                      double radius, int numPoints) {
        List<double[]> points = new ArrayList<>(numPoints);

        // 计算方向向量 d
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;

        // 计算圆心 C（取中点）
        double cx = (x1 + x2) / 2;
        double cy = (y1 + y2) / 2;
        double cz = (z1 + z2) / 2;

        // 计算与方向向量 d 垂直的向量 u
        double ux, uy, uz;
        if (Math.abs(dx) > Math.abs(dy)) {
            // 如果 dx 较大，选择 (0, 1, 0) 作为候选向量
            ux = 0;
            uy = 1;
            uz = 0;
        } else {
            // 否则选择 (1, 0, 0) 作为候选向量
            ux = 1;
            uy = 0;
            uz = 0;
        }

        // 叉积计算正交向量 u
        double uCrossX = dy * uz - dz * uy;
        double uCrossY = dz * ux - dx * uz;
        double uCrossZ = dx * uy - dy * ux;

        // 归一化向量 u
        double uLength = Math.sqrt(uCrossX * uCrossX + uCrossY * uCrossY + uCrossZ * uCrossZ);
        ux = uCrossX / uLength;
        uy = uCrossY / uLength;
        uz = uCrossZ / uLength;

        // 再次叉积计算正交向量 v
        double vx = dy * uz - dz * uy;
        double vy = dz * ux - dx * uz;
        double vz = dx * uy - dy * ux;

        // 归一化向量 v
        double vLength = Math.sqrt(vx * vx + vy * vy + vz * vz);
        vx /= vLength;
        vy /= vLength;
        vz /= vLength;

        // 生成圆上的点
        for (int i = 0; i < numPoints; i++) {
            double theta = 2 * Math.PI * i / numPoints; // 参数化角度
            double cosTheta = Math.cos(theta);
            double sinTheta = Math.sin(theta);

            // 计算圆上的点
            double px = cx + radius * (cosTheta * ux + sinTheta * vx);
            double py = cy + radius * (cosTheta * uy + sinTheta * vy);
            double pz = cz + radius * (cosTheta * uz + sinTheta * vz);

            points.add(new double[]{px, py, pz});
        }

        return points;
    }

    public static List<double[]> generateTriangleEdges(double x1, double y1, double z1,
                                                       double x2, double y2, double z2,
                                                       double sideLength, int numPoints, boolean isSymmetric) {
        List<double[]> edgePoints = new ArrayList<>();

        // 计算方向向量 d
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;

        // 计算圆心 C（取中点）
        double cx = (x1 + x2) / 2;
        double cy = (y1 + y2) / 2;
        double cz = (z1 + z2) / 2;

        // 计算与方向向量 d 垂直的向量 u
        double ux, uy, uz;
        if (Math.abs(dx) > Math.abs(dy)) {
            // 如果 dx 较大，选择 (0, 1, 0) 作为候选向量
            ux = 0;
            uy = 1;
            uz = 0;
        } else {
            // 否则选择 (1, 0, 0) 作为候选向量
            ux = 1;
            uy = 0;
            uz = 0;
        }

        // 叉积计算正交向量 u
        double uCrossX = dy * uz - dz * uy;
        double uCrossY = dz * ux - dx * uz;
        double uCrossZ = dx * uy - dy * ux;

        // 归一化向量 u
        double uLength = Math.sqrt(uCrossX * uCrossX + uCrossY * uCrossY + uCrossZ * uCrossZ);
        ux = uCrossX / uLength;
        uy = uCrossY / uLength;
        uz = uCrossZ / uLength;

        // 再次叉积计算正交向量 v
        double vx = dy * uz - dz * uy;
        double vy = dz * ux - dx * uz;
        double vz = dx * uy - dy * ux;

        // 归一化向量 v
        double vLength = Math.sqrt(vx * vx + vy * vy + vz * vz);
        vx /= vLength;
        vy /= vLength;
        vz /= vLength;

        // 计算外接圆半径
        double radius = sideLength / Math.sqrt(3);

        // 生成等边三角形的三个顶点
        double[][] vertices = new double[3][3];
        for (int k = 0; k < 3; k++) {
            double theta = 2 * Math.PI * k / 3; // 参数化角度
            double cosTheta = Math.cos(theta);
            double sinTheta = Math.sin(theta);

            // 计算三角形的顶点
            vertices[k][0] = cx + radius * (cosTheta * ux + sinTheta * vx);
            vertices[k][1] = cy + radius * (cosTheta * uy + sinTheta * vy);
            vertices[k][2] = cz + radius * (cosTheta * uz + sinTheta * vz);
        }

        // 如果需要生成对称三角形，则对顶点进行对称变换
        if (isSymmetric) {
            // 计算原三角形的中心点
            double ox = (vertices[0][0] + vertices[1][0] + vertices[2][0]) / 3;
            double oy = (vertices[0][1] + vertices[1][1] + vertices[2][1]) / 3;
            double oz = (vertices[0][2] + vertices[1][2] + vertices[2][2]) / 3;

            // 对每个顶点进行对称变换
            for (int i = 0; i < 3; i++) {
                vertices[i][0] = 2 * ox - vertices[i][0];
                vertices[i][1] = 2 * oy - vertices[i][1];
                vertices[i][2] = 2 * oz - vertices[i][2];
            }
        }

        // 生成三条边的点
        for (int i = 0; i < 3; i++) {
            int next = (i + 1) % 3; // 下一个顶点索引
            double[] start = vertices[i];
            double[] end = vertices[next];

            for (int j = 0; j < numPoints; j++) {
                double t = (double) j / (numPoints - 1); // 插值参数
                double x = start[0] + t * (end[0] - start[0]);
                double y = start[1] + t * (end[1] - start[1]);
                double z = start[2] + t * (end[2] - start[2]);

                edgePoints.add(new double[]{x, y, z});
            }
        }

        return edgePoints;
    }
}
