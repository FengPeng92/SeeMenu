import MenuUpload from './components/MenuUpload';

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100 py-12">
      <div className="container mx-auto px-4">
        <div className="text-center mb-8">
          <h1 className="text-5xl font-bold text-gray-800 mb-4">SeeMenu</h1>
          <p className="text-xl text-gray-600">
            Scan your restaurant menu and discover detailed dish information
          </p>
          <p className="text-sm text-gray-500 mt-2">
            Deployed via AWS CDK - Serverless Lambda Architecture
          </p>
        </div>
        <MenuUpload />
      </div>
    </div>
  );
}
