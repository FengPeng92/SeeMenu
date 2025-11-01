'use client';

import { useState } from 'react';
import axios from 'axios';

interface DishInfo {
  name: string;
  description?: string;
  price?: string;
  ingredients?: string[];
  allergens?: string[];
  dietaryInfo?: string[];
  nutritionalInfo?: string;
}

interface MenuAnalysisResponse {
  success: boolean;
  message: string;
  dishes?: DishInfo[];
}

export default function MenuUpload() {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [preview, setPreview] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [result, setResult] = useState<MenuAnalysisResponse | null>(null);

  const handleFileSelect = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      setSelectedFile(file);
      const reader = new FileReader();
      reader.onloadend = () => {
        setPreview(reader.result as string);
      };
      reader.readAsDataURL(file);
      setResult(null);
    }
  };

  const handleUpload = async () => {
    if (!selectedFile) return;

    setLoading(true);

    try {
      // Send raw bytes instead of FormData to avoid multipart complexity
      const arrayBuffer = await selectedFile.arrayBuffer();
      const API_URL = process.env.NEXT_PUBLIC_API_URL || 'https://pea41p50hh.execute-api.us-east-1.amazonaws.com/prod';

      const response = await axios.post<MenuAnalysisResponse>(
        `${API_URL}/api/menu/upload`,
        arrayBuffer,
        {
          headers: {
            'Content-Type': selectedFile.type || 'application/octet-stream',
            'X-Filename': selectedFile.name,
          },
        }
      );
      setResult(response.data);
    } catch (error) {
      setResult({
        success: false,
        message: 'Failed to upload menu. Please make sure the backend is running.',
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="w-full max-w-4xl mx-auto p-6">
      <div className="bg-white rounded-lg shadow-lg p-8">
        <h2 className="text-3xl font-bold mb-6 text-gray-800">Upload Menu</h2>

        <div className="mb-6">
          <label className="block mb-2 text-sm font-medium text-gray-700">
            Select a menu image
          </label>
          <input
            type="file"
            accept="image/*"
            onChange={handleFileSelect}
            className="block w-full text-sm text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 focus:outline-none p-2.5"
          />
        </div>

        {preview && (
          <div className="mb-6">
            <h3 className="text-lg font-semibold mb-2 text-gray-700">Preview:</h3>
            <img
              src={preview}
              alt="Menu preview"
              className="max-w-full h-auto rounded-lg border border-gray-300"
            />
          </div>
        )}

        <button
          onClick={handleUpload}
          disabled={!selectedFile || loading}
          className="w-full bg-blue-600 hover:bg-blue-700 disabled:bg-gray-400 text-white font-bold py-3 px-4 rounded-lg transition duration-200"
        >
          {loading ? 'Analyzing Menu...' : 'Analyze Menu'}
        </button>

        {result && (
          <div className="mt-6">
            <div className={`p-4 rounded-lg ${result.success ? 'bg-green-50' : 'bg-red-50'}`}>
              <h3 className="text-lg font-semibold mb-2 text-gray-800">
                {result.success ? 'Analysis Complete' : 'Error'}
              </h3>
              <p className="text-gray-700 mb-4">{result.message}</p>

              {result.dishes && result.dishes.length > 0 && (
                <div className="space-y-4">
                  <h4 className="font-semibold text-gray-800">Dishes Found:</h4>
                  {result.dishes.map((dish, index) => (
                    <div key={index} className="bg-white p-4 rounded-lg shadow">
                      <h5 className="font-bold text-lg text-gray-800">{dish.name}</h5>
                      {dish.description && (
                        <p className="text-gray-600 mt-2">{dish.description}</p>
                      )}
                      {dish.price && (
                        <p className="text-green-600 font-semibold mt-2">{dish.price}</p>
                      )}
                      {dish.ingredients && dish.ingredients.length > 0 && (
                        <div className="mt-2">
                          <span className="font-semibold text-gray-700">Ingredients: </span>
                          <span className="text-gray-600">{dish.ingredients.join(', ')}</span>
                        </div>
                      )}
                      {dish.allergens && dish.allergens.length > 0 && (
                        <div className="mt-2">
                          <span className="font-semibold text-red-600">Allergens: </span>
                          <span className="text-gray-600">{dish.allergens.join(', ')}</span>
                        </div>
                      )}
                      {dish.dietaryInfo && dish.dietaryInfo.length > 0 && (
                        <div className="mt-2">
                          <span className="font-semibold text-gray-700">Dietary Info: </span>
                          {dish.dietaryInfo.map((info, i) => (
                            <span
                              key={i}
                              className="inline-block bg-blue-100 text-blue-800 text-xs px-2 py-1 rounded mr-2"
                            >
                              {info}
                            </span>
                          ))}
                        </div>
                      )}
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
