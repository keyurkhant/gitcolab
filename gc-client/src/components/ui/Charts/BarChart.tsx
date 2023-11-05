import React, { useEffect, useRef } from "react";
import { Card } from "react-bootstrap";
import Chart from "chart.js/auto";

interface BarChartProps {
    label: string;
    data: number[];
    labels: string[];

}

const BarChart: React.FC<BarChartProps> = ({ label, data, labels }) => {
    const chartRef = useRef<HTMLCanvasElement>(null);
    const chartInstanceRef = useRef<Chart | null>(null);

    useEffect(() => {
        if (chartRef.current) {
            if (chartInstanceRef.current) {
                chartInstanceRef.current.destroy();
            }

            const ctx = chartRef.current.getContext("2d");

            if (ctx) {
                chartInstanceRef.current = new Chart(ctx, {
                    type: "bar",
                    data: {
                        labels: labels,
                        datasets: [
                            {
                                label: label,
                                data: data,
                                backgroundColor: "rgba(75, 192, 192, 0.6)",
                                borderColor: "rgba(75, 192, 192, 1)",
                                borderWidth: 1,
                            },
                        ],
                    },
                    options: {
                        responsive: true,
                        maintainAspectRatio: false,
                        scales: {
                            y: {
                                beginAtZero: true,
                            },
                        },
                    },
                });
            }
        }
    }, [label, data, labels]);

    return (
        <Card>
            <Card.Body>
                <canvas height="450px" ref={chartRef}></canvas>
            </Card.Body>
        </Card>
    );
};

export default BarChart;